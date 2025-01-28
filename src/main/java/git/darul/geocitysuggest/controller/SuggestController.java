package git.darul.geocitysuggest.controller;

import git.darul.geocitysuggest.constants.Constants;
import git.darul.geocitysuggest.entity.City;
import git.darul.geocitysuggest.services.GeoSuggestService;
import git.darul.geocitysuggest.utils.FileLoader;
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
        List<City> cities = geoSuggestService.findByName("darul");
//        TODO: response api diperbaiki
        return ResponseEntity.ok(cities);
    }
}
