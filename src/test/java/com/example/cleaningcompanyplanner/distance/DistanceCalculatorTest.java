package com.example.cleaningcompanyplanner.distance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ActiveProfiles("test")
class DistanceCalculatorTest {

    @Autowired
    DistanceCalculator distanceCalculator;

    @Test
    public void shouldCalculateDistanceCorrect() {

        // given
        String city = "Warszawa|Pruszków";

        // when
        double distanceBetweenCities = distanceCalculator.calculateDistanceBetweenCities(city);

        // then
        assertEquals(15, distanceBetweenCities);
    }

    @Test
    public void shouldCalculateDistanceWrong() {

        // given
        String city = "Warszawa|Berlin";

        // when
        double distanceBetweenCities = distanceCalculator.calculateDistanceBetweenCities(city);

        // then
        assertNotEquals(20, distanceBetweenCities);
    }
}