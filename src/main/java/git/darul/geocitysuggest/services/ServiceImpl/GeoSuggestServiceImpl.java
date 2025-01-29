package git.darul.geocitysuggest.services.ServiceImpl;

import git.darul.geocitysuggest.dto.request.GeoSuggestRequest;
import git.darul.geocitysuggest.dto.response.GeoSuggestResponse;
import git.darul.geocitysuggest.entity.City;
import git.darul.geocitysuggest.repository.CityRepository;
import git.darul.geocitysuggest.services.GeoSuggestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeoSuggestServiceImpl implements GeoSuggestService {

    private final CityRepository cityRepository;

    @Override
    public List<GeoSuggestResponse> findByName(GeoSuggestRequest request) {
        List<City> cities = cityRepository.findByName(request.getQuery());
        return cities.stream()
                .map(city -> getGeoSuggestResponse(city, request.getLatitude(), request.getLongitude()))
                .collect(Collectors.toList());
    }

    private GeoSuggestResponse getGeoSuggestResponse(City city, Double latitude, Double longitude) {
        return GeoSuggestResponse.builder()
                .name(String.format("%s, %s, %s", city.getName(), city.getAdmin1(), city.getCountry()))
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .score(calculateScore(city, latitude, longitude))
                .build();
    }

    private double calculateScore(City city, Double latitude, Double longitude) {
        if (latitude != null && longitude != null) {
            double distance = haversine(city.getLatitude(), city.getLongitude(), latitude, longitude);
            double maxDistance = 3000.0;
            double normalizedScore = 1 - (distance / maxDistance);
            double finalScore = Math.max(0.1, Math.min(1.0, normalizedScore));
            return Math.round(finalScore * 10.0) / 10.0;
        }
        return 1.0;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
