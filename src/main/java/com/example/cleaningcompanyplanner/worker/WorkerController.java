package com.example.cleaningcompanyplanner.worker;

import com.example.cleaningcompanyplanner.mapstruct.ObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkerController {

    private final WorkerService workerService;

    private final ObjectsMapper objectsMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkerDto createWorker(@Valid @RequestBody WorkerDto workerDto) {
        Worker worker = objectsMapper.dtoWorkerToWorker(workerDto);
        return objectsMapper.workerToDtoWorker(workerService.createWorker(worker));
    }

    @GetMapping("/{uuid}")
    public WorkerDto getWorkerById(@PathVariable String uuid) {
        return objectsMapper.workerToDtoWorker(workerService.getWorkerByUuid(uuid));
    }

    @GetMapping
    public Page<WorkerDto> getWorkers(Pageable pageable) {
        return workerService.findWorkers(pageable).map(objectsMapper::workerToDtoWorker);

    }

    @PutMapping({"/{uuid}"})
    public void updateWorker(@Valid @RequestBody WorkerDto workerDto, @PathVariable String uuid) {
        Worker worker = objectsMapper.dtoWorkerToWorker(workerDto);
         workerService.updateWorker(worker, uuid);
    }

    @DeleteMapping({"/{uuid}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorker(@PathVariable String uuid) {
        workerService.deleteWorker(uuid);
    }
}
