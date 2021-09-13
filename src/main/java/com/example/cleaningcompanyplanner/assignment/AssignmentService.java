package com.example.cleaningcompanyplanner.assignment;

import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.client.ClientService;
import com.example.cleaningcompanyplanner.distance.DistanceController;
import com.example.cleaningcompanyplanner.worker.Worker;
import com.example.cleaningcompanyplanner.worker.WorkerNotFoundException;
import com.example.cleaningcompanyplanner.worker.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final ClientService clientService;
    private final WorkerService workerService;
    private final DistanceController distanceController;

    public Assignment createAssignment(Assignment assignment) {
        if (isEndDateIsAfterStartDate(assignment) == true) {
            return assignmentRepository.save(assignment);
        }
        throw new AssignmentCannotCreateException("An assignment cannot end before it begins");
    }

    public Optional<Assignment> getAssignment(int id) {
        return assignmentRepository.findById(id);
    }

    public Page<Assignment> findAssignments(Pageable pageable) {
        return assignmentRepository.findAll(pageable);
    }

    public Assignment updateAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Assignment saveWorkerToAssignment(int assignmentId, int workerId) {

        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new AssignmentNotFoundException(assignmentId));
        Worker worker = workerService.getWorkerById(workerId).orElseThrow(() -> new WorkerNotFoundException(workerId));

        if (conditionsWorkerToAssignment(assignmentId, workerId)) {
            assignment.getWorkers().add(worker);
            return assignmentRepository.save(assignment);
        }
        throw new AssignmentCannotCreateException("The conditions for save worker to the assignment are not met");
    }

    public boolean conditionsWorkerToAssignment(int assignmentId, int workerId) {

        Worker worker = workerService.getWorkerById(workerId).orElseThrow(() -> new WorkerNotFoundException(workerId));
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new AssignmentNotFoundException(assignmentId));

        String joinedCities = getJoinedCities(assignment.getClient(), worker);
        int maxWorkersNeededForAssignment = countWorkersNeededForAssignment(assignment.getClient());
        double distanceBetweenCities = distanceController.calculateDistanceBetweenCities(joinedCities);

        if (areAssignmentsOverlapping(assignmentId, workerId) == true) {
            throw new AssignmentCannotCreateException("This worker already has assignment in these dates");
        }
        if (worker.getMaxDistanceFromCity() >= distanceBetweenCities
                && maxWorkersNeededForAssignment >= assignment.getWorkers().size() + 1) {
            return true;
        }
        throw new AssignmentCannotCreateException("Requirements do not allow this assignment");
    }

    public void deleteAssignment(int id) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new AssignmentNotFoundException(id));
        assignment.getWorkers().clear();
        assignmentRepository.deleteById(assignment.getId());
    }

    private String getJoinedCities(Client client, Worker worker) {
        String joinedCities = "";
        String clientCity = client.getCity();
        String workerCity = worker.getCity();
        joinedCities = clientCity + "|" + workerCity;

        return joinedCities;
    }

    private int countWorkersNeededForAssignment(Client client) {

        double clientArea = client.getArea();
        int workers = (int) (clientArea / 200);
        double modulo = clientArea % 200;

        if (modulo > 0) {
            return workers + 1;
        }
        return workers;
    }

    private boolean areAssignmentsOverlapping(int assignmentId, int workerId) {
        Assignment newAssignmentForWorker = assignmentRepository.findById(assignmentId).orElseThrow(() -> new AssignmentNotFoundException(assignmentId));
        Worker worker = workerService.getWorkerById(workerId).orElseThrow(() -> new WorkerNotFoundException(workerId));

        for (Assignment assignment : worker.getAssignments()) {
            if (newAssignmentForWorker.getEndDate().isAfter(assignment.getStartDate()) &&
                    newAssignmentForWorker.getEndDate().isBefore(assignment.getEndDate())) {
                return true;
            }
            if (newAssignmentForWorker.getStartDate().isAfter(assignment.getStartDate()) &&
                    newAssignmentForWorker.getStartDate().isBefore(assignment.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    private boolean isEndDateIsAfterStartDate(Assignment assignment) {
        if (assignment.getEndDate().isAfter(assignment.getStartDate())) {
            return true;
        }
        return false;
    }
}

