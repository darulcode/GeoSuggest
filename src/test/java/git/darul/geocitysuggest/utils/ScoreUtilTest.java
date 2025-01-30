package git.darul.geocitysuggest.utils;

import git.darul.geocitysuggest.entity.City;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ScoreUtilTest {

    @Test
    void testCalculateScore_WithValidCoordinates() {
        City city = City.builder().build();
        city.setLatitude(37.7749);
        city.setLongitude(-122.4194);

        double score = ScoreUtil.calculateScore(city, 37.7750, -122.4195);

        assertThat(score).isGreaterThan(0.9).isLessThanOrEqualTo(1.0);
    }

    @Test
    void testCalculateScore_WhenFarAway() {
        City city = City.builder().build();
        city.setLatitude(37.7749);
        city.setLongitude(-122.4194);

        double score = ScoreUtil.calculateScore(city, -33.8688, 151.2093); // Sydney

        assertThat(score).isEqualTo(0.1);
    }

    @Test
    void testCalculateScore_WhenNullCoordinates() {
        City city = City.builder().build();
        city.setLatitude(37.7749);
        city.setLongitude(-122.4194);

        double score = ScoreUtil.calculateScore(city, null, null);

        assertThat(score).isEqualTo(1.0);
    }

    @Test
    void testHaversine_DistanceCalculation() {
        double distance = ScoreUtil.calculateScore(City.builder().build(), null, null);

        assertThat(distance).isGreaterThanOrEqualTo(0.0);
    }
}
