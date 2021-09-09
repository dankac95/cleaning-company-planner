package com.example.cleaningcompanyplanner.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Slf4j
public class DistanceController {

    private final RetrofitClient retrofitClient;

    @GetMapping("/count")
    @EventListener()
    public double calculateDistanceBetweenCities(String cities) {

        Distance distance = getDistance((cities));
        log.info("W run example " + distance.toString());
        return distance.getDistance();
    }

    private Distance getDistance(String startCity) {
        DistanceClient distanceClient = getDistanceClientImpl();
        Response<Distance> distanceBetweenCities = null;

        try {
            distanceBetweenCities = distanceClient.getDistance(startCity).execute();
            log.info("W try catch " + distanceBetweenCities);
        } catch (IOException e) {
            System.err.println("Cities doesn't exist");
        }
        return distanceBetweenCities.body();
    }

    private DistanceClient getDistanceClientImpl() {
        return retrofitClient.getRetrofitClient().create(DistanceClient.class);
    }
}
