package com.example.cleaningcompanyplanner.worker;

import com.example.cleaningcompanyplanner.assignment.Assignment;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "worker")
public class Worker extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String pesel;

    @Column(nullable = false)
    private LocalDate employmentSince;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private boolean delegation;

    @Column(nullable = false)
    private double maxDistanceFromCity;

    @ManyToMany(mappedBy = "workers", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Assignment> assignments = new HashSet<>();
}
