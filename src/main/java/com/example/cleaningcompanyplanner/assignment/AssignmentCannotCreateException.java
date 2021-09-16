package com.example.cleaningcompanyplanner.assignment;

import org.springframework.web.bind.annotation.ResponseStatus;

public class AssignmentCannotCreateException extends RuntimeException {

    public AssignmentCannotCreateException(String message) {
        super(message);
    }
}
