package com.example.cleaningcompanyplanner.worker;

import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
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
}
