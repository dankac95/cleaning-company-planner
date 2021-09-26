package com.example.cleaningcompanyplanner.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class ClientControllerIntegrationTests {

    @Autowired
    ClientController clientController;

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
    public void shouldCreateClientAndGetClientById() {
        // given

        // when

        // then
    }

    @Test
    public void shouldCreateClientAndDeleteToListClientEqualsZero() {
        // given

        // when

        // then
        // TODO-purban: Zawolaj GET /client/{idKtoregoNiepowinenZnalez} i zaloz ze dostajesz 404 lub wyjatek
    }

    @Test
    public void shouldListSizeEquals2() {

        // given

        // when

        //then
    }

    @Test
    public void shouldUpdateClient() {

        // given

        // when

        //then
    }
}
