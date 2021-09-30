package com.example.cleaningcompanyplanner.mapstruct;

import com.example.cleaningcompanyplanner.assignment.Assignment;
import com.example.cleaningcompanyplanner.assignment.AssignmentDto;
import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.client.ClientDto;
import com.example.cleaningcompanyplanner.worker.Worker;
import com.example.cleaningcompanyplanner.worker.WorkerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ObjectsMapper {

    @Mapping(source = "firstName", target = "name")
    Worker dtoWorkerToWorker(WorkerDto workerDto);

    @Mapping(source = "name", target = "firstName")
    @Mapping(source = "uuid", target = "uuid")
    WorkerDto workerToDtoWorker(Worker worker);

    @Mapping(source = "clientName", target = "name")
    Client dtoClientToClient(ClientDto clientDto);

    @Mapping(source = "name", target = "clientName")
    ClientDto clientToDtoClient(Client client);

    Assignment dtoAssignmentToAssignment(AssignmentDto assignmentDto);

    @Mapping(source = "uuid", target = "uuid")
    AssignmentDto assignmentToDtoAssignment(Assignment assignment);
}
