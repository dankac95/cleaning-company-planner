package com.example.cleaningcompanyplanner.client;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public void createClient(@RequestBody Client client) {
        clientService.createClient(client);
    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable int id) {
        return clientService.getClientById(id);
    }

    @GetMapping
    public Page<Client> getClients(Pageable pageable) {
        return clientService.findClients(pageable);
    }

    @PutMapping({"/{id}"})
    public Client updateClient(@RequestBody Client client, @PathVariable int id) {

        Client updatedClient = clientService.getClientById(id).orElseThrow(() -> new ClientNotFoundException(id));
        updatedClient.setName(client.getName());
        updatedClient.setCity(client.getCity());
        updatedClient.setArea(client.getArea());
        updatedClient.setPricePerMeter(client.getPricePerMeter());
        return clientService.updateClient(updatedClient);
    }

    @DeleteMapping({"/{id}"})
    public void deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
    }
}
