package com.example.cleaningcompanyplanner.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        Client client = clientController.createClient(new Client("MDV", "Pruszków", 200, BigDecimal.valueOf(44)));

        // when
        Optional<Client> clientById = clientController.getClientById(client.getId());

        // then
        assertEquals(1, clientById.get().getId());
    }

    @Test
    public void shouldCreateClientAndDeleteToListClientEqualsZero() {

        // given
        Client client = clientController.createClient(new Client("MDV", "Pruszków", 200, BigDecimal.valueOf(44)));

        // when
        clientController.deleteClient(client.getId());

        // then
        assertEquals(0, clientController.findAllClients().size());
    }


    @Test
    public void shouldListSizeEquals2() {

        // given
        clientController.createClient(new Client("MDV", "Pruszków", 200, BigDecimal.valueOf(44)));
        clientController.createClient(new Client("Fire", "Pruszków", 100, BigDecimal.valueOf(8)));

        // when
        List<Client> allClients = clientController.findAllClients();

        //then
        assertEquals(2, allClients.size());
    }

    @Test
    public void shouldUpdateClient() {

        // given
        clientController.createClient(new Client("MDV", "Pruszków", 200, BigDecimal.valueOf(44)));
        Client client = new Client("Fire", "Pruszków", 100, BigDecimal.valueOf(8));

        // when
        Client updatedClient = clientController.updateClient(client, 1);

        //then
        assertEquals("Fire", updatedClient.getName());
    }
}
