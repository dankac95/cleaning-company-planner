package com.example.cleaningcompanyplanner.worker;

import com.example.cleaningcompanyplanner.jpa.BaseEntity;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Worker extends BaseEntity {

    private String name;
    private String lastName;
    private String pesel;
    private LocalDate dateOfEmployment;
    private String phoneNumber;
    private String emailAdress;
    private String workerCity;
    private double maxDistanceFromCity;
}
