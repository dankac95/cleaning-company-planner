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
        return clientService.findClient(pageable);
    }

    @PutMapping
    public void updateClient(@RequestBody Client client) {
        clientService.updateClient(client);
    }

    @DeleteMapping
    public void deleteClient(@RequestParam int id) {
        clientService.deleteClient(id);
    }
}
