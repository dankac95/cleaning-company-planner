package com.example.cleaningcompanyplanner.worker;

import com.example.cleaningcompanyplanner.assignment.Assignment;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "worker")
public class Worker extends BaseEntity {

    private String name;

    private String lastName;

    private String pesel;

    private LocalDate employmentSince;

    private String phoneNumber;

    private String email;

    private String city;

    private boolean delegation;

    private double maxDistanceFromCity;

    @ManyToMany(mappedBy = "workers", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Assignment> assignments = new HashSet<>();

    public Worker(String name, String lastName, String pesel, LocalDate employmentSince, String phoneNumber, String email, String city, boolean delegation, double maxDistanceFromCity) {
        this.name = name;
        this.lastName = lastName;
        this.pesel = pesel;
        this.employmentSince = employmentSince;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.city = city;
        this.delegation = delegation;
        this.maxDistanceFromCity = maxDistanceFromCity;
    }
}
