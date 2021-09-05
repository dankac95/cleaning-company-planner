package com.example.cleaningcompanyplanner.assignment;

import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import com.example.cleaningcompanyplanner.worker.Worker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Assignment extends BaseEntity {

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany
    @JoinColumn(name = "worker_id")
    private List<Worker> worker;

}
