package com.example.cleaningcompanyplanner.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;

    public Worker createWorker(Worker worker) {
        checkDelegationAgreed(worker);
        return workerRepository.save(worker);
    }

    private void checkDelegationAgreed(Worker worker) {
        if (!worker.isDelegation()) {
            worker.setMaxDistanceFromCity(0);
        }
    }

    public Optional<Worker> getWorkerById(int id) {
        return workerRepository.findById(id);
    }

    public Page<Worker> findWorkers(Pageable pageable) {
        return workerRepository.findAll(pageable);
    }

    public Worker updateWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public void deleteWorker(int id) {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new WorkerNotFoundException(id));
        workerRepository.deleteById(worker.getId());
    }
}
