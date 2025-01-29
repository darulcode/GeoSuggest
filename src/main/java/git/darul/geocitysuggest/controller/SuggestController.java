package git.darul.geocitysuggest.controller;

import git.darul.geocitysuggest.constants.Constants;
import git.darul.geocitysuggest.dto.request.GeoSuggestRequest;
import git.darul.geocitysuggest.dto.response.GeoSuggestResponse;
import git.darul.geocitysuggest.services.GeoSuggestService;
import git.darul.geocitysuggest.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        List<GeoSuggestResponse> responses = geoSuggestService.suggestByNameAndLocation(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constants.SUCCESS_GET_SUGGEST, responses);
    }

    @GetMapping("/city")
    public ResponseEntity<?> getSuggestionsCity(@RequestParam String name) {
        List<String> response = geoSuggestService.suggestCityName(name);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constants.SUCCESS_GET_SUGGEST, response);
    }
}
