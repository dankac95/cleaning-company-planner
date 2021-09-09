package com.example.cleaningcompanyplanner.assignment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping()
    public void createAssignment(@RequestBody Assignment assignment) {
        assignmentService.createAssignment(assignment);
    }

    @GetMapping("/{id}")
    public Optional<Assignment> getAssignmentById(@PathVariable int id) {
        return assignmentService.getAssignment(id);
    }

    @GetMapping
    public Page<Assignment> getAssignments(Pageable pageable) {
        return assignmentService.findAssignments(pageable);
    }

    @PutMapping({"/{id}"})
    public Assignment updateAssignment(@RequestBody Assignment assignment, @PathVariable int id) {

        Assignment updatedAssignment = assignmentService.getAssignment(id).orElseThrow(() -> new AssignmentNotFoundException(id));
        updatedAssignment.setStartDate(assignment.getStartDate());
        updatedAssignment.setEndDate(assignment.getEndDate());
        updatedAssignment.setClient(assignment.getClient());
        updatedAssignment.setWorkers(assignment.getWorkers());
        return assignmentService.updateAssignment(updatedAssignment);
    }

    @DeleteMapping({"/{id}"})
    public void deleteAssignment(@PathVariable int id) {
        assignmentService.deleteAssignment(id);
    }
}
