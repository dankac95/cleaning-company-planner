package com.example.cleaningcompanyplanner.assignment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AssignmentNotFoundException extends RuntimeException {

    public AssignmentNotFoundException(int id) {
        super("Assignment with id " + id + " not found");
    }
}
