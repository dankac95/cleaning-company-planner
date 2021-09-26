package com.example.cleaningcompanyplanner.assignment;

import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.worker.Worker;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@Getter
@Setter
public class AssignmentDto {

    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;

    @Future(message = "End date must be in future")
    private LocalDate endDate;

    @Hidden
    private Client client;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Worker> workers = new HashSet<>();
}
