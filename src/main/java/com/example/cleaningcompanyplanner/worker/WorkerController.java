package com.example.cleaningcompanyplanner.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkerController {

    private final WorkerService workerService;

    @PostMapping
    public void createWorker(@RequestBody Worker worker) {
        workerService.createWorker(worker);
    }

    @GetMapping("/{id}")
    public Optional<Worker> getWorkerById(@PathVariable int id) {
        return workerService.getWorkerById(id);
    }

    @GetMapping("/pagination")
    public Page<Worker> getWorkers(Pageable pageable) {
        return workerService.findWorkers(pageable);
    }

    @PutMapping({"/{id}"})
    public Worker updateWorker(@RequestBody Worker worker, @PathVariable int id) {

        Worker updatedWorker = workerService.getWorkerById(id).orElseThrow(() -> new WorkerNotFoundException(id));
        updatedWorker.setName(worker.getName());
        updatedWorker.setLastName(worker.getLastName());
        updatedWorker.setPesel(worker.getPesel());
        updatedWorker.setEmploymentSince(worker.getEmploymentSince());
        updatedWorker.setPhoneNumber(worker.getPhoneNumber());
        updatedWorker.setEmail(worker.getEmail());
        updatedWorker.setCity(worker.getCity());
        updatedWorker.setDelegation(worker.isDelegation());
        updatedWorker.setMaxDistanceFromCity(worker.getMaxDistanceFromCity());
        return workerService.updateWorker(updatedWorker);
    }

    @DeleteMapping({"/{id}"})
    public void deleteWorker(@PathVariable int id) {
        workerService.deleteWorker(id);
    }
}
