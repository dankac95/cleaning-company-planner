package com.example.cleaningcompanyplanner.worker;

import com.example.cleaningcompanyplanner.assignment.Assignment;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "worker")
public class Worker extends BaseEntity {

    @Column(nullable = false)
    @Size(min = 2, message = " name too short")
    private String name;

    @Column(nullable = false)
    @Size(min = 2, message = "last name too short")
    private String lastName;

    @Column(nullable = false)
    @Size(min = 11, max = 11, message = "Pesel has 11 numbers")
    private String pesel;

    @Column(nullable = false)
    @PastOrPresent
    private LocalDate employmentSince;

    @Column(nullable = false)
    @Size(min = 9, max = 12)
    private String phoneNumber;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @Size(min = 2, message = "Too short word")
    private String city;

    @Column(nullable = false)
    private boolean delegation;

    @Column(nullable = false)
    @Max(value = 999, message = "Max distance is 999 km from worker city")
    @PositiveOrZero(message = "Distance must be over 0")
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
