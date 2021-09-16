package com.example.cleaningcompanyplanner.assignment;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/client/{clientId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAssignment(@Valid @RequestBody Assignment assignment, @PathVariable int clientId) {
        assignmentService.createAssignment(assignment, clientId);
    }

    @GetMapping("/{id}")
    public Optional<Assignment> getAssignmentById(@PathVariable int id) {
        return assignmentService.getAssignment(id);
    }

    @GetMapping("/pagination")
    public Page<Assignment> getAssignments(@Parameter(hidden = true) Pageable pageable, @RequestParam("page") int page) {
        return assignmentService.findAssignments(pageable);
    }

    @GetMapping
    public List<Assignment> findAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @PutMapping({"/{id}"})
    public Assignment updateAssignment(@RequestBody Assignment assignment, @PathVariable int id) {
        return assignmentService.updateAssignment(assignment, id);
    }

    @PutMapping("/{assignmentId}/worker/{workerId}")
    public Assignment saveWorkersToAssignment(@PathVariable int assignmentId, @PathVariable int workerId) {
        return assignmentService.saveWorkerToAssignment(assignmentId, workerId);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(@PathVariable int id) {
        assignmentService.deleteAssignment(id);
    }
}
