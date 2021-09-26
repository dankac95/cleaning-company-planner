package com.example.cleaningcompanyplanner.worker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class WorkerNotFoundException extends RuntimeException {

    public WorkerNotFoundException(String uuid) {
        super("Worker with id " + uuid + " not found");
    }
}
