package com.example.cleaningcompanyplanner.assignment;

import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.jpa.BaseEntity;
import com.example.cleaningcompanyplanner.worker.Worker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "assignment")
@ToString(exclude = "client")
public class Assignment extends BaseEntity {

    @Column(nullable = false)
    @FutureOrPresent(message = "Start date cannot be in the past")

    private LocalDate startDate;

    @Column(nullable = false)
    @Future(message = "End date must be in future")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Hidden
    private Client client;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @ManyToMany
    @JoinTable(
            name = "assignment_worker",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    private Set<Worker> workers = new HashSet<>();

}
