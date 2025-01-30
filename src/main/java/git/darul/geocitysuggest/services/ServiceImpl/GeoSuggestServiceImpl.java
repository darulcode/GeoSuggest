package git.darul.geocitysuggest.services.ServiceImpl;

import git.darul.geocitysuggest.config.Trie;
import git.darul.geocitysuggest.constants.Constants;
import git.darul.geocitysuggest.dto.request.GeoSuggestRequest;
import git.darul.geocitysuggest.dto.response.GeoSuggestResponse;
import git.darul.geocitysuggest.entity.City;
import git.darul.geocitysuggest.repository.CityRepository;
import git.darul.geocitysuggest.services.GeoSuggestService;
import git.darul.geocitysuggest.utils.FileLoader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static git.darul.geocitysuggest.utils.ScoreUtil.calculateScore;

@Service
@RequiredArgsConstructor
public class GeoSuggestServiceImpl implements GeoSuggestService {

    private final CityRepository cityRepository;
    private final Trie trie;

    @PostConstruct
    public void init() {
        List<City> cities = FileLoader.loadCityFromCSV(Constants.PATH_FILE);
        for (City city : cities) {
            trie.insert(city.getName());
        }
    }

    @Override
    public List<GeoSuggestResponse> suggestByNameAndLocation(GeoSuggestRequest request) {
        List<City> cities = cityRepository.findByName(request.getQuery());

        return cities.stream()
                .map(city -> getGeoSuggestResponse(city, request.getLatitude(), request.getLongitude()))
                .sorted(Comparator.comparing(GeoSuggestResponse::getScore).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> suggestCityName(String prefix) {
        return cityRepository.findCityName(prefix).stream().limit(5).collect(Collectors.toList());
    }

    private GeoSuggestResponse getGeoSuggestResponse(City city, Double latitude, Double longitude) {
        return GeoSuggestResponse.builder()
                .name(String.format("%s, %s, %s", city.getName(), city.getAdmin1(), city.getCountry()))
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .score(calculateScore(city, latitude, longitude))
                .build();
    }


}
