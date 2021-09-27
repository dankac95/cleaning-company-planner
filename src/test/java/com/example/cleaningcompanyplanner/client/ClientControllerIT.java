package com.example.cleaningcompanyplanner.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Test
    public void shouldReturnClientOnTheFirstPlaceAfterSort() throws Exception {
        ClientDto clientToCreate = ClientDto.builder()
                .clientName("AaKoma")
                .city("Warszawa")
                .area(10.5)
                .pricePerMeter(BigDecimal.valueOf(12000))
                .build();

        String responseString = mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToCreate)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        ClientDto createdClient = objectMapper.readValue(responseString, ClientDto.class);

        mockMvc.perform(get("/client?size=1&sort=id,DESC"))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].uuid", equalTo(createdClient.getUuid())));
    }

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

    // Aktualizacja clienta - przypadek pozytywny - sprawdzamy czy zmienione pola sa odzwierciedlowe w GET
    @Test
    public void shouldGetClientWithChangedFieldsAfterUpdate() throws Exception {
        ClientDto clientToCreate = ClientDto.builder()
                .clientName("TestClient")
                .city("Gdynia")
                .area(100.5)
                .pricePerMeter(BigDecimal.valueOf(200))
                .build();

        ClientDto clientToUpdate = ClientDto.builder()
                .clientName("ChangeClient")
                .city("Warszawa")
                .area(50)
                .pricePerMeter(BigDecimal.valueOf(20))
                .build();

        String responseString = mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToCreate)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        ClientDto createdClient = objectMapper.readValue(responseString, ClientDto.class);

        mockMvc.perform(put("/client/" + createdClient.getUuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToUpdate)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/client/" + createdClient.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName", equalTo(clientToUpdate.getClientName())))
                .andExpect(jsonPath("$.city", equalTo(clientToUpdate.getCity())))
                .andExpect(jsonPath("$.area", equalTo(clientToUpdate.getArea())))
                .andExpect(jsonPath("$.pricePerMeter", equalTo(clientToUpdate.getPricePerMeter().doubleValue())));
    }

    // Aktualizacja - blad walidacji
    @Test
    public void shouldReturnValidationExceptionAfterUpdate() throws Exception {
        ClientDto clientToCreate = ClientDto.builder()
                .clientName("TestClient")
                .city("Gdynia")
                .area(100.5)
                .pricePerMeter(BigDecimal.valueOf(200))
                .build();

        ClientDto clientToUpdate = ClientDto.builder()
                .clientName("ChangeClient")
                .city("Warszawa")
                .area(-50)
                .pricePerMeter(BigDecimal.valueOf(20))
                .build();

        String responseString = mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToCreate)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        ClientDto createdClient = objectMapper.readValue(responseString, ClientDto.class);

        mockMvc.perform(put("/client/" + createdClient.getUuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToUpdate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].field", equalTo("area")))
                .andExpect(jsonPath("$.violations[0].message", equalTo("value can not be negative")));
    }

    // Aktualizacja - bledne ID (404)
    @Test
    public void shouldReturn404BecauseWrongId() throws Exception {
        String randomUuid = UUID.randomUUID().toString();

        ClientDto clientToUpdate = ClientDto.builder()
                .clientName("ChangeClient")
                .city("Warszawa")
                .area(50)
                .pricePerMeter(BigDecimal.valueOf(20))
                .build();

        mockMvc.perform(put("/client/" + randomUuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToUpdate)))
                .andExpect(status().isNotFound());
    }

    // Usuniecie - przypadek pozytywny (getOne zwroci 404)
    @Test
    public void shouldReturn404AfterDelete() throws Exception {
        ClientDto clientToCreate = ClientDto.builder()
                .clientName("TestClient")
                .city("Gdynia")
                .area(100.5)
                .pricePerMeter(BigDecimal.valueOf(200))
                .build();

        String responseString = mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        ClientDto createdClient = objectMapper.readValue(responseString, ClientDto.class);

        mockMvc.perform(delete("/client/" + createdClient.getUuid()));

        mockMvc.perform(get("/client/" + createdClient.getUuid()))
                .andExpect(status().isNotFound());
    }

    // Usuniecie - bledne ID
    @Test
    public void shouldReturnInfoAboutInvalidIdAfterDelete() throws Exception {
        mockMvc.perform(delete("/client/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }


}