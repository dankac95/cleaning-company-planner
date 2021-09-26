package com.example.cleaningcompanyplanner.distance;

import com.example.cleaningcompanyplanner.configuration.ConfigProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@RequiredArgsConstructor
public class DistanceClientConfiguration {   // TODO-purban: DistanceClientConfiguration VVV

    private final ObjectMapper objectMapper;

    private final ConfigProperties configProperties;

    // TODO-purban: Zamioast metody ponizej, powinienes miec metode @Bean o typie DistanceClient i tam budowac ten obiekt DistanceClient VVV

    @Bean
    public DistanceClient getRetrofitClient() {
        OkHttpClient httpClient = new OkHttpClient();
        return new Retrofit.Builder()
                .baseUrl(configProperties.getBasicUrl()) // TODO-purban: Wyciagniecie adresu do konfiguracji (application.yaml) VVV
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(httpClient)
                .build()
                .create(DistanceClient.class);
    }
}
