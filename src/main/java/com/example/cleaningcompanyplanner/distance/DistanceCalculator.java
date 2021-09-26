package com.example.cleaningcompanyplanner.distance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;

@RequiredArgsConstructor
@Component // TODO-purban: Rename + zmiana adnotacji na np. Component VVV
public class DistanceCalculator {

    private final DistanceClientConfiguration distanceClientConfiguration;

    public double calculateDistanceBetweenCities(String cities) {
        Distance distance = getDistance(cities);
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
        return distanceClientConfiguration.getRetrofitClient();
    }
}
