package com.example.cleaningcompanyplanner.client;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client getClientByUuid(String uuid) {
        return findClientByUuid(uuid);
    }

    public Page<Client> findClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client updateClient(Client client, String uuid) {
        Client updatedClient = findClientByUuid(uuid);

        updatedClient.setName(client.getName());
        updatedClient.setCity(client.getCity());
        updatedClient.setArea(client.getArea());
        updatedClient.setPricePerMeter(client.getPricePerMeter());
        return clientRepository.save(updatedClient);
    }

    public void deleteClient(String uuid) {
        Client client = findClientByUuid(uuid);
        clientRepository.deleteById(client.getId());
    }

    private Client findClientByUuid(String uuid) {
        return clientRepository.findAll()
                .stream()
                .filter(client1 -> client1.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new ClientNotFoundException(uuid));
    }
}
