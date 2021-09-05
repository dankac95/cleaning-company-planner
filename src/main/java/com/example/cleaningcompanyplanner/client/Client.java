package com.example.cleaningcompanyplanner.client;

import com.example.cleaningcompanyplanner.assignment.Assignment;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Client extends BaseEntity {

    private String name;
    private String city;
    private double area;
    private BigDecimal pricePerM2;

    @OneToMany(mappedBy = "client")
    private List<Assignment> assignments;

}
