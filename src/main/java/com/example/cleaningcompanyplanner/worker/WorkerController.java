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

    @GetMapping
    public Page<Worker> getWorkers(Pageable pageable) {
        return workerService.findWorkers(pageable);
    }

    @PutMapping
    public void updateWorker(Worker worker) {
        workerService.updateWorker(worker);
    }

    @DeleteMapping
    public void deleteWorker(@RequestParam int id) {
        workerService.deleteWorker(id);
    }

}
