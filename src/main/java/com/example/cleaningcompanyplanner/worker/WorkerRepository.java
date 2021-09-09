package com.example.cleaningcompanyplanner.worker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
}
