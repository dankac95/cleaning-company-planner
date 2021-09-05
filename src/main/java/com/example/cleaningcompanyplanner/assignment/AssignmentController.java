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

    @PostMapping
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

    @PutMapping
    public void updateAssignment(Assignment assignment) {
        assignmentService.updateAssignment(assignment);
    }

    @DeleteMapping
    public void deleteAssignment(@RequestParam int id) {
        assignmentService.deleteAssignment(id);
    }

}
