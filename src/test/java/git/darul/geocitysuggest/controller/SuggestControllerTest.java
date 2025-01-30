package git.darul.geocitysuggest.controller;

import git.darul.geocitysuggest.constants.Constants;
import git.darul.geocitysuggest.dto.request.GeoSuggestRequest;
import git.darul.geocitysuggest.dto.response.GeoSuggestResponse;
import git.darul.geocitysuggest.services.GeoSuggestService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SuggestControllerTest {

    @Mock
    private GeoSuggestService geoSuggestService;

    @InjectMocks
    private SuggestController suggestController;

    private MockMvc mockMvc;

    public SuggestControllerTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(suggestController).build();
    }

    @Test
    void testGetSuggestions() throws Exception {
        GeoSuggestResponse response1 = new GeoSuggestResponse("London", 51.5074, -0.1278, 1.0);
        GeoSuggestResponse response2 = new GeoSuggestResponse("Londontown", 38.9334, -76.5494, 1.0);

        List<GeoSuggestResponse> mockResponses = Arrays.asList(response1, response2);

        when(geoSuggestService.suggestByNameAndLocation(any(GeoSuggestRequest.class))).thenReturn(mockResponses);

        mockMvc.perform(get("/suggestions?q=London"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_GET_SUGGEST))
                .andExpect(jsonPath("$.suggestions.length()").value(2));

        verify(geoSuggestService, times(1)).suggestByNameAndLocation(any(GeoSuggestRequest.class));
    }

    @Test
    void testGetSuggestionsCity() throws Exception {
        List<String> mockCityNames = Arrays.asList("London", "Londontowne");

        when(geoSuggestService.suggestCityName(anyString())).thenReturn(mockCityNames);

        mockMvc.perform(get("/suggestions/city?name=London"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_GET_SUGGEST))
                .andExpect(jsonPath("$.suggestions.length()").value(2));

        verify(geoSuggestService, times(1)).suggestCityName(anyString());
    }
}
