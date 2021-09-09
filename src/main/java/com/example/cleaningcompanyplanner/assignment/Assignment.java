package com.example.cleaningcompanyplanner.assignment;

import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import com.example.cleaningcompanyplanner.worker.Worker;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "assignment")
@ToString(exclude = "client_id")
public class Assignment extends BaseEntity {

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties("assignments")
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "assignment_worker",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    private Set<Worker> workers = new HashSet<>();
}
