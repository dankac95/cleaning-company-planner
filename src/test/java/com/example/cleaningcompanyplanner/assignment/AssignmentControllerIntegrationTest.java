package com.example.cleaningcompanyplanner.assignment;

import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.client.ClientService;
import com.example.cleaningcompanyplanner.worker.Worker;
import com.example.cleaningcompanyplanner.worker.WorkerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class AssignmentControllerIntegrationTest {

    @Autowired
    AssignmentController assignmentController;

    @Autowired
    ClientService clientService;

    @Autowired
    WorkerService workerService;

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
    @Transactional
    public void shouldCreateAssignmentAndGetAssignmentById() {

        // given
        givenClient();
        givenWorker();
        Assignment assignment = assignmentController.createAssignment(new Assignment(LocalDate.parse("2021-10-01"), LocalDate.parse("2021-11-01")), givenClient().getId());
        assignment.getWorkers().add(givenWorker());
        assignment.setClient(givenClient());

        // when
        Optional<Assignment> assignmentById = assignmentController.getAssignmentById(assignment.getId());

        // then
        assertEquals(1, assignmentById.get().getId());
        assertEquals("Fire", assignmentById.get().getClient().getName());
        assertEquals(1, assignmentById.get().getWorkers().size());
    }

    @Test
    @Transactional
    public void shouldCreateAssignmentAndDeleteToListAssignmentsEqualsZero() {

        // given
        Assignment assignment = assignmentController.createAssignment(new Assignment(LocalDate.parse("2021-10-01"), LocalDate.parse("2021-11-01")), givenClient().getId());
        assignment.getWorkers().add(givenWorker());

        // when
        assignmentController.deleteAssignment(assignment.getId());

        // then
        assertEquals(0, assignmentController.findAllAssignments().size());
    }


    @Test
    public void shouldListSizeEquals2() {

        // given
        assignmentController.createAssignment(new Assignment(LocalDate.parse("2021-10-01"), LocalDate.parse("2021-11-01")), givenClient().getId());
        assignmentController.createAssignment(new Assignment(LocalDate.parse("2021-10-01"), LocalDate.parse("2021-11-01")), givenClient().getId());

        // when
        List<Assignment> allAssignments = assignmentController.findAllAssignments();

        //then
        assertEquals(2, allAssignments.size());
    }

    @Test
    @Transactional
    public void shouldUpdateAssignment() {

        // given
        assignmentController.createAssignment(new Assignment(LocalDate.parse("2021-10-01"), LocalDate.parse("2021-11-01")), givenClient().getId());
        Assignment assignment = new Assignment(LocalDate.parse("2021-10-01"), LocalDate.parse("2021-11-01"));
        assignment.getWorkers().add(givenWorker());

        // when
        Assignment updatedAssignment = assignmentController.updateAssignment(assignment, 1);

        //then
        assertEquals(LocalDate.parse("2021-10-01"), updatedAssignment.getStartDate());
    }

    private Client givenClient() {
        return clientService.createClient(new Client("Fire", "Pruszków", 100, BigDecimal.valueOf(8)));
    }

    private Worker givenWorker() {
        return workerService.createWorker(new Worker("Daniel", "Kacprzak", "90010517555",
                LocalDate.parse("2020-05-01"), "508808595", "kaczpak@gmail.com", "Gdynia", true, 20));
    }
}