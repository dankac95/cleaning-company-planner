package com.example.cleaningcompanyplanner.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest({AssignmentController.class})
public class AssignmentControllerApiTest {

    @MockBean
    AssignmentController assignmentController;

    @MockBean
    AssignmentRepository assignmentRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldCreateWorker() throws Exception {
        Assignment assignment = new Assignment(LocalDate.parse("2002-01-01"), LocalDate.parse("2020-04-15"));

        mockMvc.perform(post("/assignment/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignment)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllWorkers() throws Exception {
        Assignment assignment = new Assignment(LocalDate.parse("2022-01-01"), LocalDate.parse("2022-04-15"));


        mockMvc.perform(get("/assignment"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateWorker() throws Exception {
        Assignment assignment = new Assignment(LocalDate.parse("2022-01-01"), LocalDate.parse("2022-04-15"));

        mockMvc.perform(put("/assignment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignment)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteWorker() throws Exception {
        Assignment assignment = new Assignment(LocalDate.parse("2022-01-01"), LocalDate.parse("2022-04-15"));

        mockMvc.perform(delete("/assignment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignment)))
                .andExpect(status().isNoContent());
    }
}
