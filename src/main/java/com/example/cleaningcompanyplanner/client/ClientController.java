package com.example.cleaningcompanyplanner.client;

import com.example.cleaningcompanyplanner.mapstruct.ObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    private final ObjectsMapper objectsMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto createClient(@RequestBody ClientDto clientDto) {
        Client client = objectsMapper.dtoClientToClient(clientDto);
        return objectsMapper.clientToDtoClient(clientService.createClient(client));
    }

    @GetMapping("/{uuid}")
    public ClientDto getClientById(@PathVariable String uuid) {
        return objectsMapper.clientToDtoClient(clientService.getClientByUuid(uuid));
    }

    // TODO-purban: Zostawisaz tylko endpoint z Pageable, wywalasz /list, @GetMapping dla pageable bez zadnego adresu VVV
    @GetMapping
    public Page<ClientDto> getClients(Pageable pageable) {
        return clientService.findClients(pageable).map(objectsMapper::clientToDtoClient);
    }

    @PutMapping({"/{uuid}"})
    public void updateClient(@RequestBody ClientDto clientDto, @PathVariable String uuid) {
        Client client = objectsMapper.dtoClientToClient(clientDto);
        clientService.updateClient(client, uuid);
    }

    @DeleteMapping({"/{uuid}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable String uuid) {
        clientService.deleteClient(uuid);
    }
}
