package com.example.cleaningcompanyplanner.client;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getClientById(int id) {
        return clientRepository.findById(id);
    }

    public Page<Client> findClient(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }


}
