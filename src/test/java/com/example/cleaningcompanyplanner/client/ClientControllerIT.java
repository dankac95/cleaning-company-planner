package com.example.cleaningcompanyplanner.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Po zalozeniu klienta, moge go pobrac
    @Test
    public void shouldFindClientWhenCreated() throws Exception {
        ClientDto clientToCreate = ClientDto.builder()
                .clientName("Test")
                .city("Warszawa")
                .area(10.5)
                .pricePerMeter(BigDecimal.valueOf(12000))
                .build();

        String responseString = mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        ClientDto createdClient = objectMapper.readValue(responseString, ClientDto.class);

        mockMvc.perform(get("/client/" + createdClient.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName", equalTo(clientToCreate.getClientName())))
                .andExpect(jsonPath("$.city", equalTo(clientToCreate.getCity())))
                .andExpect(jsonPath("$.area", equalTo(clientToCreate.getArea())))
                .andExpect(jsonPath("$.pricePerMeter", equalTo(clientToCreate.getPricePerMeter().doubleValue())));

    }

    // Po zalozeniu klienta robimy getList (z sortowaniem id,DESC) i zakladamy ze dodany client jest na poczatku listy
    // Nie moge zalozyc klienta ze wzgledu na bledy walidacji (celowy bledny request + weryfikacja response)
    @Test
    public void shouldNotCreateInvalidClientEntry() throws Exception {
        ClientDto clientToCreate = ClientDto.builder()
                .clientName("T")
                .city("Warszawa")
                .area(10.5)
                .pricePerMeter(BigDecimal.valueOf(12000))
                .build();

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].field", equalTo("clientName")))
                .andExpect(jsonPath("$.violations[0].message", equalTo("name too short")));
    }

    // Proba pobrania client o blednym ID
    @Test
    public void shouldReturnHttp404ForInvalidClientId() throws Exception {
        String randomUuid = UUID.randomUUID().toString();

        mockMvc.perform(get("/client/" + randomUuid))
                .andExpect(status().isNotFound());
    }
    // Aktualizacja clienta - przyupadek pozytywny - sprawdzamy czy zmienione pola sa odzwierciedlowe w GET
    // Aktualizacja - blad walidacji
    // Aktualizacja - bledne ID (404)
    // Usuniecie - przypadek pozytywny (getOne zwroci 404)
    // Usuniecie - bledne ID
}
