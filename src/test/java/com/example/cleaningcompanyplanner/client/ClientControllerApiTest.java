package com.example.cleaningcompanyplanner.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ClientController.class})
@ActiveProfiles("test")
public class ClientControllerApiTest {

    @MockBean
    ClientRepository clientRepository;

    @MockBean
    ClientController clientController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldCreateWorker() throws Exception {

        Client client = new Client("MDV", "Warszawa", 500, BigDecimal.valueOf(20));

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllWorkers() throws Exception {
        Client client = new Client("MDV", "Warszawa", 500, BigDecimal.valueOf(20));


        mockMvc.perform(get("/client"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateWorker() throws Exception {

        Client client = new Client("MDV", "Warszawa", 500, BigDecimal.valueOf(20));

        mockMvc.perform(put("/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteWorker() throws Exception {

        Client client = new Client("MDV", "Warszawa", 500, BigDecimal.valueOf(20));

        mockMvc.perform(delete("/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isNoContent());
    }
}
