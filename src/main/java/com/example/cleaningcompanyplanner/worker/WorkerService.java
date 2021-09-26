package com.example.cleaningcompanyplanner.worker;

import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.client.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Worker getWorkerByUuid(String uuid) {
        return findWorkerByUuid(uuid);
    }

    public Page<Worker> findWorkers(Pageable pageable) {
        return workerRepository.findAll(pageable);
    }

    public Worker updateWorker(Worker worker, String uuid) {
        Worker updatedWorker = findWorkerByUuid(uuid);

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

    public void deleteWorker(String uuid) {
        Worker worker = findWorkerByUuid(uuid);
        workerRepository.deleteById(worker.getId());
    }

    private Worker findWorkerByUuid(String uuid) {
        return workerRepository.findAll()
                .stream()
                .filter(worker -> worker.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new WorkerNotFoundException(uuid));
    }
}
