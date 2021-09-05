package com.example.cleaningcompanyplanner.assignment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Optional<Assignment> getAssignment(int id) {
        return assignmentRepository.findById(id);
    }

    public Page<Assignment> findAssignments(Pageable pageable) {
        return assignmentRepository.findAll(pageable);
    }

    public Assignment updateAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignment(int id) {
        assignmentRepository.deleteById(id);
    }
}
