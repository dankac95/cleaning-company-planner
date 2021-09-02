CREATE TABLE assignment
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    uuid      VARCHAR(255),
    startDate DATE,
    endDate   DATE,
    client_id INT NOT NULL,
    FOREIGN KEY FK_assignment_client (client_id) REFERENCES client (id),
    worker_id INT NOT NULL,
    FOREIGN KEY FK_assignment_worker (worker_id) REFERENCES worker (id)
);