package com.example.cleaningcompanyplanner.worker;

import com.example.cleaningcompanyplanner.assignment.AssignmentController;
import com.example.cleaningcompanyplanner.client.ClientController;
import com.example.cleaningcompanyplanner.distance.DistanceClientConfiguration;
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

@WebMvcTest({WorkerController.class})
@ActiveProfiles("test")
class WorkerControllerApiTest {

    @MockBean
    WorkerRepository workerRepository;

    @MockBean
    AssignmentController assignmentController;

    @MockBean
    ClientController clientController;

    @MockBean
    DistanceClientConfiguration distanceClientConfiguration;

    @MockBean
    WorkerController workerController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldCreateWorker() throws Exception {

        Worker worker = new Worker("Daniel", "Kacprzak", "90010517555",
                LocalDate.parse("2020-05-01"), "508808595", "kaczpak@gmail.com", "Gdynia", true, 20);

        mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(worker)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllWorkers() throws Exception {
        Worker worker = new Worker("Daniel", "Kacprzak", "90010517555",
                LocalDate.parse("2020-05-01"), "508808595", "kaczpak@gmail.com", "Gdynia", true, 20);


        mockMvc.perform(get("/worker"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateWorker() throws Exception {

        Worker worker = new Worker("Daniel", "Kacprzak", "90010517555",
                LocalDate.parse("2020-05-01"), "508808595", "kaczpak@gmail.com", "Gdynia", true, 20);

        mockMvc.perform(put("/worker/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(worker)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteWorker() throws Exception {

        Worker worker = new Worker("Daniel", "Kacprzak", "90010517555",
                LocalDate.parse("2020-05-01"), "508808595", "kaczpak@gmail.com", "Gdynia", true, 20);

        mockMvc.perform(delete("/worker/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(worker)))
                .andExpect(status().isNoContent());
    }
}