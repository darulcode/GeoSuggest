package git.darul.geocitysuggest.services.ServiceImpl;

import git.darul.geocitysuggest.dto.request.GeoSuggestRequest;
import git.darul.geocitysuggest.dto.response.GeoSuggestResponse;
import git.darul.geocitysuggest.entity.City;
import git.darul.geocitysuggest.repository.CityRepository;
import git.darul.geocitysuggest.utils.ScoreUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeoSuggestServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private GeoSuggestServiceImpl geoSuggestServiceImpl;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSuggestByNameAndLocation() {
        GeoSuggestRequest request = new GeoSuggestRequest("London", 43.70011, -79.4163);

        City city1 = new City("London", 42.98339, -81.23304, "CA", "08");
        City city2 = new City("Londontowne", 38.93345, -76.54941, "US", "MD");
        City city3 = new City("London", 39.88645, -83.44825, "US", "OH");
        City city4 = new City("New London", 41.35565, -72.09952, "US", "CT");
        City city5 = new City("Londonderry", 42.86509, -71.37395, "US", "NH");
        City city6 = new City("New London", 44.39276, -88.73983, "US", "WI");
        City city7 = new City("London", 37.12898, -84.08326, "US", "KY");

        List<City> cityList = Arrays.asList(city1, city2, city3, city4, city5, city6, city7);

        when(cityRepository.findByName("London")).thenReturn(cityList);

        try (MockedStatic<ScoreUtil> mockedScoreUtil = mockStatic(ScoreUtil.class)) {
            mockedScoreUtil.when(() -> ScoreUtil.calculateScore(city1, 43.70011, -79.4163)).thenReturn(0.9);
            mockedScoreUtil.when(() -> ScoreUtil.calculateScore(city2, 43.70011, -79.4163)).thenReturn(0.8);
            mockedScoreUtil.when(() -> ScoreUtil.calculateScore(city3, 43.70011, -79.4163)).thenReturn(0.8);
            mockedScoreUtil.when(() -> ScoreUtil.calculateScore(city4, 43.70011, -79.4163)).thenReturn(0.8);
            mockedScoreUtil.when(() -> ScoreUtil.calculateScore(city5, 43.70011, -79.4163)).thenReturn(0.8);
            mockedScoreUtil.when(() -> ScoreUtil.calculateScore(city6, 43.70011, -79.4163)).thenReturn(0.8);
            mockedScoreUtil.when(() -> ScoreUtil.calculateScore(city7, 43.70011, -79.4163)).thenReturn(0.7);

            List<GeoSuggestResponse> responses = geoSuggestServiceImpl.suggestByNameAndLocation(request);

            assertNotNull(responses);
            assertEquals(7, responses.size());
            assertEquals("London, 08, CA", responses.get(0).getName());
            assertEquals(0.9, responses.get(0).getScore());
            assertEquals("London, OH, US", responses.get(2).getName());
            assertEquals(0.8, responses.get(2).getScore());
            assertEquals("London, KY, US", responses.get(6).getName());
            assertEquals(0.7, responses.get(6).getScore());

            verify(cityRepository).findByName("London");
        }
    }


    @Test
    void testSuggestCityName() {
        String prefix = "Lon";
        List<String> mockCityNames = Arrays.asList("London", "Londontowne", "Londonbridge");

        when(cityRepository.findCityName(prefix)).thenReturn(mockCityNames);

        List<String> cityNames = geoSuggestServiceImpl.suggestCityName(prefix);

        assertNotNull(cityNames);
        assertEquals(3, cityNames.size());
        assertTrue(cityNames.contains("London"));
        assertTrue(cityNames.contains("Londontowne"));
        assertTrue(cityNames.contains("Londonbridge"));

        verify(cityRepository).findCityName(prefix);
    }
}
