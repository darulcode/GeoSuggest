package git.darul.geocitysuggest.services;

import git.darul.geocitysuggest.entity.City;

import java.util.List;

public interface GeoSuggestService {
    List<City> findByName(String name);
}
