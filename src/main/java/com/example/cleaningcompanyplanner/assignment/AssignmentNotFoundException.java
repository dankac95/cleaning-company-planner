package com.example.cleaningcompanyplanner.assignment;

public class AssignmentNotFoundException extends RuntimeException {

    public AssignmentNotFoundException(int id) {
        super("Assignment with id " + id + " not found");
    }
}
