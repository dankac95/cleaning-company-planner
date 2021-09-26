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
        // when

        // then
    }

    @Test
    public void shouldCreateWorkerAndDeleteToListWorkersEqualsZero() {

        // given

        // when

        // then
    }


    @Test
    public void shouldListSizeEquals2() {

        // given

        // when

        //then
    }

    @Test
    public void shouldUpdateWorker() {

        // given

        // when

        //then
    }
}