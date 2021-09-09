package com.example.cleaningcompanyplanner.distance;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DistanceClient {

    @GET("/route.json")
    Call<Distance> getDistance(@Query("stops") String cities);
}
