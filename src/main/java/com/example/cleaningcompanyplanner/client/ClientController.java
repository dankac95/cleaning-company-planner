package com.example.cleaningcompanyplanner.client;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable int id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/pagination")
    public Page<Client> getClients(@Parameter(hidden = true) Pageable pageable, @RequestParam("page") int page) {
        return clientService.findClients(pageable);
    }

    @GetMapping("/list")
    public List<Client> findAllClients() {
        return clientService.getClientList();
    }

    @PutMapping({"/{id}"})
    public Client updateClient(@RequestBody Client client, @PathVariable int id) {
        return clientService.updateClient(client, id);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
    }
}
