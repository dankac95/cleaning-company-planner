package com.example.cleaningcompanyplanner.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class WorkerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Po zalozeniu workera, moge go pobrac
    @Test
    public void shouldFindWorkerWhenCreated() throws Exception {

        String responseString = mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workerToCreate())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        WorkerDto createdWorker = objectMapper.readValue(responseString, WorkerDto.class);

        mockMvc.perform(get("/worker/" + createdWorker.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(workerToCreate().getFirstName())))
                .andExpect(jsonPath("$.city", equalTo(workerToCreate().getCity())))
                .andExpect(jsonPath("$.email", equalTo(workerToCreate().getEmail())))
                .andExpect(jsonPath("$.maxDistanceFromCity", equalTo(workerToCreate().getMaxDistanceFromCity())));
    }

    // Po utowrzeniu z delegation false worker ma maxDistanceFromCity = 0
    @Test
    public void shouldReturnWorkerWithZeroDistanceForDelegationFalse() throws Exception {

        String responseString = mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workerDelegationFalseAndMaxDistancePositive())))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        WorkerDto createdWorker = objectMapper.readValue(responseString, WorkerDto.class);

        mockMvc.perform(get("/worker/" + createdWorker.getUuid()))
                .andExpect(jsonPath("$.city", equalTo(workerDelegationFalseAndMaxDistancePositive().getCity())))
                .andExpect(jsonPath("$.delegation", equalTo(workerDelegationFalseAndMaxDistancePositive().isDelegation())))
                .andExpect(jsonPath("$.maxDistanceFromCity", equalTo(0.0)));
    }

    // Po zalozeniu Workera robimy getList (z sortowaniem id,DESC) i zakladamy ze Worker client jest na poczatku listy
    @Test
    public void shouldReturnWorkerOnTheFirstPlaceAfterSort() throws Exception {

        String responseString = mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workerToCreate())))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        WorkerDto createdWorker = objectMapper.readValue(responseString, WorkerDto.class);

        mockMvc.perform(get("/worker?size=1&sort=id,DESC"))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].uuid", equalTo(createdWorker.getUuid())));
    }

    // Nie moge zalozyc workera ze wzgledu na bledy walidacji (celowy bledny request + weryfikacja response)
    @Test
    public void shouldNotCreateInvalidWorkerEntry() throws Exception {
        mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidWorkerToCreate())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].field", equalTo("city")))
                .andExpect(jsonPath("$.violations[0].message", equalTo("Too short word")));
    }

    // Proba pobrania client o blednym ID
    @Test
    public void shouldReturnHttp404ForInvalidWorkerId() throws Exception {
        String randomUuid = UUID.randomUUID().toString();

        mockMvc.perform(get("/worker/" + randomUuid))
                .andExpect(status().isNotFound());
    }

    // Aktualizacja workera - przypadek pozytywny - sprawdzamy czy zmienione pola sa odzwierciedlowe w GET
    @Test
    public void shouldGetWorkerWithChangedFieldsAfterUpdate() throws Exception {

        String responseString = mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workerToCreate())))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        WorkerDto createdWorker = objectMapper.readValue(responseString, WorkerDto.class);

        mockMvc.perform(put("/worker/" + createdWorker.getUuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workerToUpdate())))
                .andExpect(status().isOk());

        mockMvc.perform(get("/worker/" + createdWorker.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(workerToUpdate().getFirstName())))
                .andExpect(jsonPath("$.email", equalTo(workerToUpdate().getEmail())))
                .andExpect(jsonPath("$.phoneNumber", equalTo(workerToUpdate().getPhoneNumber())))
                .andExpect(jsonPath("$.delegation", equalTo(workerToUpdate().isDelegation())));
    }

    // Aktualizacja - blad walidacji
    @Test
    public void shouldReturnValidationExceptionAfterUpdate() throws Exception {

        String responseString = mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workerToCreate())))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        WorkerDto createdWorker = objectMapper.readValue(responseString, WorkerDto.class);

        mockMvc.perform(put("/worker/" + createdWorker.getUuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workerToInvalidUpdate())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].field", equalTo("pesel")))
                .andExpect(jsonPath("$.violations[0].message", equalTo("Pesel has 11 numbers")));
    }

    // Aktualizacja - bledne ID (404)
    @Test
    public void shouldReturn404BecauseWrongId() throws Exception {
        String randomUuid = UUID.randomUUID().toString();

        mockMvc.perform(put("/worker/" + randomUuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workerToUpdate())))
                .andExpect(status().isNotFound());
    }

    // Usuniecie - przypadek pozytywny (getOne zwroci 404)
    @Test
    public void shouldReturn404AfterDelete() throws Exception {

        String responseString = mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workerToCreate())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        WorkerDto createdWorker = objectMapper.readValue(responseString, WorkerDto.class);

        mockMvc.perform(delete("/client/" + createdWorker.getUuid()));

        mockMvc.perform(get("/client/" + createdWorker.getUuid()))
                .andExpect(status().isNotFound());
    }

    // Usuniecie - bledne ID
    @Test
    public void shouldReturnInfoAboutInvalidIdAfterDelete() throws Exception {
        mockMvc.perform(delete("/worker/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    private WorkerDto workerToUpdate() {
        return WorkerDto.builder()
                .firstName("Kazik")
                .lastName("Kowalski")
                .pesel("80041577555")
                .employmentSince(LocalDate.parse("2019-01-01"))
                .phoneNumber("884652111")
                .email("kazio@example.com")
                .city("Białystok")
                .delegation(false)
                .maxDistanceFromCity(0)
                .build();
    }

    private WorkerDto workerToInvalidUpdate() {
        return WorkerDto.builder()
                .firstName("Kazik")
                .lastName("Kowalski")
                .pesel("44")
                .employmentSince(LocalDate.parse("2019-01-01"))
                .phoneNumber("884652111")
                .email("kazio@example.com")
                .city("Białystok")
                .delegation(false)
                .maxDistanceFromCity(0)
                .build();
    }

    private WorkerDto workerDelegationFalseAndMaxDistancePositive() {
        return WorkerDto.builder()
                .firstName("Kazik")
                .lastName("Kowalski")
                .pesel("95091608639")
                .employmentSince(LocalDate.parse("2019-01-01"))
                .phoneNumber("884652111")
                .email("kazio@example.com")
                .city("Gdynia")
                .delegation(false)
                .maxDistanceFromCity(22)
                .build();
    }


    private WorkerDto workerToCreate() {
        return WorkerDto.builder()
                .firstName("Daniel")
                .lastName("Kacprzak")
                .pesel("95091608639")
                .employmentSince(LocalDate.parse("2020-01-01"))
                .phoneNumber("508808595")
                .email("daniel.kacprzak7@gmail.com")
                .city("Warszawa")
                .delegation(true)
                .maxDistanceFromCity(10)
                .build();
    }

    private WorkerDto invalidWorkerToCreate() {
        return WorkerDto.builder()
                .firstName("Daniel")
                .lastName("Kacprzak")
                .pesel("95091608639")
                .employmentSince(LocalDate.parse("2020-01-01"))
                .phoneNumber("508808595")
                .email("daniel.kacprzak7@gmail.com")
                .city("I")
                .delegation(true)
                .maxDistanceFromCity(10)
                .build();
    }
}
