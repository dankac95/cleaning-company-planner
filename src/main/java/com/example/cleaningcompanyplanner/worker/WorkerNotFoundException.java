package com.example.cleaningcompanyplanner.worker;

public class WorkerNotFoundException extends RuntimeException{

    public WorkerNotFoundException(int id) {
        super("Worker with id " + id + " not found");
    }
}
