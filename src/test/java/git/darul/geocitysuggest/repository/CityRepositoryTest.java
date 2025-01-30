package git.darul.geocitysuggest.repository;

import git.darul.geocitysuggest.config.Trie;
import git.darul.geocitysuggest.constants.Constants;
import git.darul.geocitysuggest.entity.City;
import git.darul.geocitysuggest.utils.FileLoader;
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

class CityRepositoryTest {

    @Mock
    private Trie trie;


    @InjectMocks
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindByName() {
        City city1 = new City("London", 42.98339, -81.23304, "CA", "08");
        City city2 = new City("Londontowne", 38.93345, -76.54941, "US", "MD");
        City city3 = new City("London", 39.88645, -83.44825, "US", "OH");

        List<City> mockCities = Arrays.asList(city1, city2, city3);

        try (MockedStatic<FileLoader> fileLoaderMockedStatic = mockStatic(FileLoader.class)) {
            fileLoaderMockedStatic.when(() -> FileLoader.loadCityFromCSV(Constants.PATH_FILE)).thenReturn(mockCities);
            List<City> cities = cityRepository.findByName("London");

            assertNotNull(cities);
            assertEquals(3, cities.size());
            assertTrue(cities.contains(city1));
            assertTrue(cities.contains(city3));

            fileLoaderMockedStatic.verify(() -> FileLoader.loadCityFromCSV(Constants.PATH_FILE), times(1));
        }
    }


    @Test
    void testFindCityName() {
        List<String> mockCityNames = Arrays.asList("London", "Londontowne", "Londonbridge");
        when(trie.searchByPrefix("Lon")).thenReturn(mockCityNames);
        List<String> cityNames = cityRepository.findCityName("Lon");

        assertNotNull(cityNames);
        assertEquals(3, cityNames.size());
        assertTrue(cityNames.contains("London"));
        assertTrue(cityNames.contains("Londontowne"));
        assertTrue(cityNames.contains("Londonbridge"));

        verify(trie, times(1)).searchByPrefix("Lon");
    }
}
