package com.example.cleaningcompanyplanner.assignment;

import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.mapstruct.ObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    private final ObjectsMapper objectsMapper;

    @PostMapping("/client/{clientUuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentDto createAssignment(@Valid @RequestBody AssignmentDto assignmentDto, @PathVariable String clientUuid) {
        Assignment assignment = objectsMapper.dtoAssignmentToAssignment(assignmentDto);
        Assignment createdAssignment = assignmentService.createAssignment(assignment,clientUuid);
        return objectsMapper.assignmentToDtoAssignment(createdAssignment);
    }

    @GetMapping("/{uuid}")
    public AssignmentDto getAssignmentById(@PathVariable String uuid) {
        return objectsMapper.assignmentToDtoAssignment(assignmentService.getAssignmentByUuid(uuid));
    }

    @GetMapping
    public Page<AssignmentDto> getAssignments(Pageable pageable) {
        return assignmentService.findAssignments(pageable).map(objectsMapper::assignmentToDtoAssignment);
    }

    @PutMapping({"/{uuid}"})
    public void updateAssignment(@Valid @RequestBody AssignmentDto assignmentDto, @PathVariable String uuid) {
        Assignment assignment = objectsMapper.dtoAssignmentToAssignment(assignmentDto);
        assignmentService.updateAssignment(assignment, uuid);
    }

    @PutMapping("/{assignmentUuid}/worker/{workerUuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void saveWorkersToAssignment(@Valid @PathVariable String assignmentUuid, @PathVariable String workerUuid) {
        assignmentService.saveWorkerToAssignment(assignmentUuid, workerUuid);
    }

    @DeleteMapping({"/{uuid}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(@PathVariable String uuid) {
        assignmentService.deleteAssignment(uuid);
    }
}
