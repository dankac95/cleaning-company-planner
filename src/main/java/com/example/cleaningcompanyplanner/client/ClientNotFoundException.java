package com.example.cleaningcompanyplanner.client;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(int id) {
        super("Client with id " + id + " not found");
    }
}
