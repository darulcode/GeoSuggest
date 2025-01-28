package git.darul.geocitysuggest.services.ServiceImpl;

import git.darul.geocitysuggest.constants.Constants;
import git.darul.geocitysuggest.entity.City;
import git.darul.geocitysuggest.services.GeoSuggestService;
import git.darul.geocitysuggest.utils.FileLoader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoSuggestServiceImpl implements GeoSuggestService {

    @Override
    public List<City> findByName(String name) {
        List<City> cities = FileLoader.loadCityFromCSV(Constants.PATH_FILE);
        return cities;
    }
}
