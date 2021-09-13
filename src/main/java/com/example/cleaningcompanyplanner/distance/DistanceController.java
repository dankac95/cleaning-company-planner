package com.example.cleaningcompanyplanner.distance;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class DistanceController {

    private final RetrofitClient retrofitClient;


    @EventListener()
    public double calculateDistanceBetweenCities(String cities) {

        Distance distance = getDistance((cities));
        return distance.getDistance();
    }

    private Distance getDistance(String startCity) {
        DistanceClient distanceClient = getDistanceClientImpl();
        Response<Distance> distanceBetweenCities = null;

        try {
            distanceBetweenCities = distanceClient.getDistance(startCity).execute();
        } catch (IOException e) {
            System.err.println("Cities doesn't exist");
        }
        return distanceBetweenCities.body();
    }

    private DistanceClient getDistanceClientImpl() {
        return retrofitClient.getRetrofitClient().create(DistanceClient.class);
    }
}
