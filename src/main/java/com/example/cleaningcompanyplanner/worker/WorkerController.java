package com.example.cleaningcompanyplanner.worker;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkerController {

    private final WorkerService workerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Worker createWorker(@RequestBody Worker worker) {
        return workerService.createWorker(worker);
    }

    @GetMapping("/{id}")
    public Optional<Worker> getWorkerById(@PathVariable int id) {
        return workerService.getWorkerById(id);
    }

    @GetMapping("/pagination")
    public Page<Worker> getWorkers(@Parameter(hidden = true) Pageable pageable, @RequestParam("page") int page) {
        return workerService.findWorkers(pageable);
    }

    @GetMapping("/list")
    public List<Worker> findAllWorkers() {
        return workerService.getWorkerList();
    }

    @PutMapping({"/{id}"})
    public Worker updateWorker(@RequestBody Worker worker, @PathVariable int id) {
        return workerService.updateWorker(worker, id);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorker(@PathVariable int id) {
        workerService.deleteWorker(id);
    }
}
