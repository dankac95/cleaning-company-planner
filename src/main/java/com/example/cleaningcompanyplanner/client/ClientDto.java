package com.example.cleaningcompanyplanner.client;

import com.example.cleaningcompanyplanner.assignment.Assignment;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ClientDto {

    @Size(min = 2, message = "name too short")
    private String clientName;

    @Size(min = 2, message = "city word too short")
    private String city;

    @Positive
    private double area;

    @Positive
    private BigDecimal pricePerMeter;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Assignment> assignments;
}
