package com.example.cleaningcompanyplanner.assignment;

import com.example.cleaningcompanyplanner.client.Client;
import com.example.cleaningcompanyplanner.client.ClientService;
import com.example.cleaningcompanyplanner.distance.DistanceCalculator;
import com.example.cleaningcompanyplanner.worker.Worker;
import com.example.cleaningcompanyplanner.worker.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final ClientService clientService;
    private final WorkerService workerService;
    private final DistanceCalculator distanceCalculator;

    public Assignment createAssignment(Assignment assignment, String clientUuid) {
        Client client = clientService.getClientByUuid(clientUuid);
        if (isEndDateIsAfterStartDate(assignment) == true) {
            assignment.setClient(client);
            return assignmentRepository.save(assignment);
        }
        throw new AssignmentCannotCreateException("An assignment cannot end before it begins");
    }

    public Assignment getAssignmentByUuid(String uuid) {
        return findAssignmentByUuid(uuid);
    }

    public Page<Assignment> findAssignments(Pageable pageable) {
        return assignmentRepository.findAll(pageable);
    }

    public Assignment updateAssignment(Assignment assignment, String uuid) {

        Assignment updatedAssignment = findAssignmentByUuid(uuid);
        updatedAssignment.setStartDate(assignment.getStartDate());
        updatedAssignment.setEndDate(assignment.getEndDate());
        updatedAssignment.getWorkers().clear();
        return assignmentRepository.save(updatedAssignment);
    }

    public Assignment saveWorkerToAssignment(String assignmentUuid, String workerUuid) {

        Assignment assignment = findAssignmentByUuid(assignmentUuid);
        Worker worker = workerService.getWorkerByUuid(workerUuid);

        if (conditionsWorkerToAssignment(assignmentUuid, workerUuid)) {
            assignment.getWorkers().add(worker);
            return assignmentRepository.save(assignment);
        }
        throw new AssignmentCannotCreateException("The conditions for save worker to the assignment are not met");
    }

    private boolean conditionsWorkerToAssignment(String assignmentUuid, String workerUuid) {

        Worker worker = workerService.getWorkerByUuid(workerUuid);
        Assignment assignment = findAssignmentByUuid(assignmentUuid);

        String joinedCities = getJoinedCities(assignment.getClient(), worker);
        int maxWorkersNeededForAssignment = countWorkersNeededForAssignment(assignment.getClient());
        double distanceBetweenCities = distanceCalculator.calculateDistanceBetweenCities(joinedCities);

        if (areAssignmentsOverlapping(assignmentUuid, workerUuid) == true) {
            throw new AssignmentCannotCreateException("This worker already has assignment in these dates");
        }
        if (worker.getMaxDistanceFromCity() >= distanceBetweenCities
                && maxWorkersNeededForAssignment >= assignment.getWorkers().size() + 1) {
            return true;
        }
        throw new AssignmentCannotCreateException("Requirements do not allow this assignment. Max space for 1 worker is 200m2");
    }

    public void deleteAssignment(String uuid) {
        Assignment assignment = findAssignmentByUuid(uuid);
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

    private boolean areAssignmentsOverlapping(String assignmentUuid, String workerUuid) {
        Assignment newAssignmentForWorker = findAssignmentByUuid(assignmentUuid);
        Worker worker = workerService.getWorkerByUuid(workerUuid);

        for (Assignment assignment : worker.getAssignments()) {
            if (newAssignmentForWorker.getEndDate().isAfter(assignment.getStartDate()) &&
                    newAssignmentForWorker.getEndDate().isBefore(assignment.getEndDate())) {
                return true;
            }
            if (newAssignmentForWorker.getStartDate().isAfter(assignment.getStartDate()) &&
                    newAssignmentForWorker.getStartDate().isBefore(assignment.getEndDate())) {
                return true;
            }
            if (newAssignmentForWorker.getStartDate().isEqual(assignment.getStartDate()) &&
                    newAssignmentForWorker.getEndDate().equals(assignment.getEndDate())) {
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

    private Assignment findAssignmentByUuid(String uuid) {
        return assignmentRepository.findAll()
                .stream()
                .filter(assignment -> assignment.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(uuid));
    }
}

