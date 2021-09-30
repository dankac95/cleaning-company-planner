package com.example.cleaningcompanyplanner.worker;

import com.example.cleaningcompanyplanner.assignment.Assignment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDto {

    private String uuid;

    @Size(min = 2, message = "name too short")
    private String firstName;

    @Size(min = 2, message = "last name too short")
    private String lastName;

    @Size(min = 11, max = 11, message = "Pesel has 11 numbers")
    private String pesel;

    @PastOrPresent(message = "cannot be enployment before present")
    private LocalDate employmentSince;

    @Size(min = 9, max = 12, message = "phone has 9-12 digits")
    private String phoneNumber;

    @Email(message = "Email is incorrect")
    private String email;

    @Size(min = 2, message = "Too short word")
    private String city;

    private boolean delegation;

    @Max(value = 999, message = "Max distance is 999 km from worker city")
    @PositiveOrZero(message = "Distance must be over 0")
    private double maxDistanceFromCity;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Assignment> assignments = new HashSet<>();


}
