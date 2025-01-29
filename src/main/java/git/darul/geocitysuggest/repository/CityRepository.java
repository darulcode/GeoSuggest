package git.darul.geocitysuggest.repository;

import git.darul.geocitysuggest.constants.Constants;
import git.darul.geocitysuggest.entity.City;
import git.darul.geocitysuggest.utils.FileLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepository {
    public List<City> findByName(String name) {
        List<City> cities = FileLoader.loadCityFromCSV(Constants.PATH_FILE);
        return cities.stream().filter(city -> city.getName().toLowerCase().contains(name.toLowerCase())).toList();
    }
}
