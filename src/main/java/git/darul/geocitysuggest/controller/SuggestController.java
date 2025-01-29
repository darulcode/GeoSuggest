package git.darul.geocitysuggest.controller;

import git.darul.geocitysuggest.constants.Constants;
import git.darul.geocitysuggest.dto.request.GeoSuggestRequest;
import git.darul.geocitysuggest.dto.response.GeoSuggestResponse;
import git.darul.geocitysuggest.services.GeoSuggestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.SUGGEST_API)
public class SuggestController {

    private final GeoSuggestService geoSuggestService;

    @GetMapping
    public ResponseEntity<?> getSuggestions(
            @RequestParam(name = "q") String query,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ) {
        GeoSuggestRequest request = GeoSuggestRequest.builder()
                .query(query)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        List<GeoSuggestResponse> responses = geoSuggestService.findByName(request);
//        TODO: response api diperbaiki
        return ResponseEntity.ok(responses);
    }
}
