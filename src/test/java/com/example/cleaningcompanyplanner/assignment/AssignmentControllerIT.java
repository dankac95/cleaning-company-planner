package com.example.cleaningcompanyplanner.assignment;

import com.example.cleaningcompanyplanner.client.ClientDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldFindAssignmentByGetWhenCreated() throws Exception {

        String responseClientString = mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToCreate())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        ClientDto createdClient = objectMapper.readValue(responseClientString, ClientDto.class);

        String responseAssignmentString = mockMvc.perform(post("/assignment/client/" + createdClient.getUuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentToCreate())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        AssignmentDto createdAssignment = objectMapper.readValue(responseAssignmentString, AssignmentDto.class);

        mockMvc.perform(get("/assignment/" + createdAssignment.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startDate", equalTo(assignmentToCreate().getStartDate().toString())))
                .andExpect(jsonPath("$.endDate", equalTo(assignmentToCreate().getEndDate().toString())))
                .andExpect(jsonPath("$.client.city", equalTo(createdAssignment.getClient().getCity())))
                .andExpect(jsonPath("$.client.area", equalTo(createdAssignment.getClient().getArea())));
    }

    @Test
    public void shouldNotCreateAssignmentInvalidValdation() throws Exception {

        String responseClientString = mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToCreate())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        ClientDto createdClient = objectMapper.readValue(responseClientString, ClientDto.class);

        mockMvc.perform(post("/assignment/client/" + createdClient.getUuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAssignmentToCreate())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].field", equalTo("endDate")))
                .andExpect(jsonPath("$.violations[0].message", equalTo("End date must be in future")));
    }

    @Test
    public void shouldReturnHttp404ForInvalidAssignmentId() throws Exception {
        String randomUuid = UUID.randomUUID().toString();

        mockMvc.perform(get("/assignment/" + randomUuid))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnInfoAboutInvalidIdAfterDelete() throws Exception {
        mockMvc.perform(delete("/assignment/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    private AssignmentDto assignmentToCreate() {
        return AssignmentDto.builder()
                .startDate(LocalDate.parse("2022-01-01"))
                .endDate(LocalDate.parse("2023-02-02"))
                .build();
    }

    private AssignmentDto invalidAssignmentToCreate() {
        return AssignmentDto.builder()
                .startDate(LocalDate.parse("2022-01-01"))
                .endDate(LocalDate.parse("2019-02-02"))
                .build();
    }

    private ClientDto clientToCreate() {
        return ClientDto.builder()
                .clientName("Test")
                .city("Warszawa")
                .area(10.5)
                .pricePerMeter(BigDecimal.valueOf(12000))
                .build();
    }
}
