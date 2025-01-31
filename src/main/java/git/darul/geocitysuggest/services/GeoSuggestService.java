package git.darul.geocitysuggest.services;

import git.darul.geocitysuggest.dto.request.GeoSuggestRequest;
import git.darul.geocitysuggest.dto.response.GeoSuggestResponse;

import java.util.List;

public interface GeoSuggestService {
    List<GeoSuggestResponse> suggestByNameAndLocation(GeoSuggestRequest request);
    List<String> suggestCityName(String prefix);
}
