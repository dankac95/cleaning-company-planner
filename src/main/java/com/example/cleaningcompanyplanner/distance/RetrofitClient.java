package com.example.cleaningcompanyplanner.distance;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitClient {

    public Retrofit getRetrofitClient() {
        OkHttpClient httpClient = new OkHttpClient();
        return new Retrofit.Builder()
                .baseUrl("https://www.dystans.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }
}
