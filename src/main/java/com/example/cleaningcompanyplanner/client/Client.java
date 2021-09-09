package com.example.cleaningcompanyplanner.client;

import com.example.cleaningcompanyplanner.assignment.Assignment;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "client")
@ToString(exclude = "assignments")
public class Client extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private double area;

    @Column(nullable = false)
    private BigDecimal pricePerMeter;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "client")
    @JsonIgnoreProperties("client")
    private List<Assignment> assignments;

}