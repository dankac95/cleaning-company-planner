package com.example.cleaningcompanyplanner.worker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class WorkerControllerIntegrationTest {

    @Autowired
    WorkerController workerController;

    @Container
    private static MySQLContainer container = new MySQLContainer("mysql:8.0")
            .withDatabaseName("cleaning_db")
            .withUsername("daniel")
            .withPassword("pass123");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    public void shouldCreateWorkerAndGetWorkerById() {

        // given
        Worker worker = workerController.createWorker(new Worker("Daniel", "Kacprzak", "90010517555",
                LocalDate.parse("2020-05-01"), "508808595", "kaczpak@gmail.com", "Gdynia", true, 20));
        // when
        Optional<Worker> workerById = workerController.getWorkerById(worker.getId());

        // then
        assertEquals(1, workerById.get().getId());
    }

    @Test
    public void shouldCreateWorkerAndDeleteToListWorkersEqualsZero() {

        // given
        Worker worker = workerController.createWorker(new Worker("Daniel", "Kacprzak", "90010517555",
                LocalDate.parse("2020-05-01"), "508808595", "kaczpak@gmail.com", "Gdynia", true, 20));

        // when
        workerController.deleteWorker(worker.getId());

        // then
        assertEquals(0, workerController.findAllWorkers().size());
    }


    @Test
    public void shouldListSizeEquals2() {

        // given
        workerController.createWorker(new Worker("Daniel", "Kacprzak", "90010517555",
                LocalDate.parse("2020-05-01"), "508808595", "kaczpak@gmail.com", "Gdynia", true, 20));
        workerController.createWorker(new Worker("Kamil", "Nowak", "87091765425",
                LocalDate.parse("2021-01-01"), "640798411", "jasnik@gmail.com", "Białystok", false, 0));

        // when
        List<Worker> allWorkers = workerController.findAllWorkers();

        //then
        assertEquals(2, allWorkers.size());
    }

    @Test
    public void shouldUpdateWorker() {

        // given
        workerController.createWorker(new Worker("Daniel", "Kacprzak", "90010517555",
                LocalDate.parse("2020-05-01"), "508808595", "kaczpak@gmail.com", "Gdynia", true, 20));
        Worker worker = new Worker("Kamil", "Nowak", "87091765425",
                LocalDate.parse("2021-01-01"), "640798411", "jasnik@gmail.com", "Białystok", false, 0);

        // when
        Worker updatedWorker = workerController.updateWorker(worker, 1);

        //then
        assertEquals("Białystok", updatedWorker.getCity());
    }
}