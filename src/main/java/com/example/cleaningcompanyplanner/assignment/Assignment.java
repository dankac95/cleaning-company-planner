package com.example.cleaningcompanyplanner.assignment;

import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import com.example.cleaningcompanyplanner.worker.Worker;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Assignment extends BaseEntity {

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany
    @JoinColumn(name = "worker_id")
    private List<Worker> worker;
}
