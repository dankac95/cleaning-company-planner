package com.example.cleaningcompanyplanner.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return Optional.ofNullable(workerRepository.findById(id).orElseThrow(() -> new WorkerNotFoundException(id)));
    }

    public Page<Worker> findWorkers(Pageable pageable) {
        return workerRepository.findAll(pageable);
    }

    public List<Worker> getWorkerList() {
        return workerRepository.findAll();
    }

    public Worker updateWorker(Worker worker, int workerId) {
        Worker updatedWorker = workerRepository.findById(workerId).orElseThrow(() -> new WorkerNotFoundException(workerId));
        updatedWorker.setName(worker.getName());
        updatedWorker.setLastName(worker.getLastName());
        updatedWorker.setPesel(worker.getPesel());
        updatedWorker.setEmploymentSince(worker.getEmploymentSince());
        updatedWorker.setPhoneNumber(worker.getPhoneNumber());
        updatedWorker.setEmail(worker.getEmail());
        updatedWorker.setCity(worker.getCity());
        updatedWorker.setDelegation(worker.isDelegation());
        updatedWorker.setMaxDistanceFromCity(worker.getMaxDistanceFromCity());
        if (!updatedWorker.isDelegation()) {
            updatedWorker.setMaxDistanceFromCity(0);
        }
        return workerRepository.save(updatedWorker);
    }

    public void deleteWorker(int id) {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new WorkerNotFoundException(id));
        workerRepository.deleteById(worker.getId());
    }
}
