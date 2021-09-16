package com.example.cleaningcompanyplanner.client;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getClientById(int id) {
        return Optional.ofNullable(clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id)));
    }

    public List<Client> getClientList() {
        return clientRepository.findAll();
    }

    public Page<Client> findClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client updateClient(Client client, int clientId) {
        Client updatedClient = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        updatedClient.setName(client.getName());
        updatedClient.setCity(client.getCity());
        updatedClient.setArea(client.getArea());
        updatedClient.setPricePerMeter(client.getPricePerMeter());
        return clientRepository.save(updatedClient);
    }

    public void deleteClient(int id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository.deleteById(client.getId());
    }
}
