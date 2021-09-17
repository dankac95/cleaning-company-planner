package com.example.cleaningcompanyplanner.assignment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssignmentCannotCreateException extends RuntimeException {

    public AssignmentCannotCreateException(String message) {
        super(message);
    }
}
