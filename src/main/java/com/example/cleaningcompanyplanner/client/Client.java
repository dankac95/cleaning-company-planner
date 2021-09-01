package com.example.cleaningcompanyplanner.client;

import com.example.cleaningcompanyplanner.jpa.BaseEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Client extends BaseEntity {

    private String name;
    private String city;
    private double area;
    private BigDecimal pricePerM2;

}
