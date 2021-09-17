package com.example.cleaningcompanyplanner.client;

import com.example.cleaningcompanyplanner.assignment.Assignment;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
    @Size(min = 2)
    private String name;

    @Column(nullable = false)
    @Size(min = 2)
    private String city;

    @Column(nullable = false)
    @Positive
    private double area;

    @Column(nullable = false)
    @Positive
    private BigDecimal pricePerMeter;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "client")
    @JsonIgnore
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Assignment> assignments;

    public Client(String name, String city, double area, BigDecimal pricePerMeter) {
        this.name = name;
        this.city = city;
        this.area = area;
        this.pricePerMeter = pricePerMeter;
    }
}