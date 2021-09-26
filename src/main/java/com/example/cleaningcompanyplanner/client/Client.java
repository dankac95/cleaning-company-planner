package com.example.cleaningcompanyplanner.client;

import com.example.cleaningcompanyplanner.assignment.Assignment;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "client")
@ToString(exclude = "assignments")
public class Client extends BaseEntity {

    private String name;

    private String city;

    private double area;

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