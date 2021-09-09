CREATE TABLE assignment
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    uuid       VARCHAR(255),
    start_date DATE NOT NULL,
    end_date   DATE NOT NULL,
    client_id  INT,
    FOREIGN KEY FK_assignment_client (client_id) REFERENCES client (id),
    worker_id  INT,
    FOREIGN KEY FK_assignment_worker (worker_id) REFERENCES worker (id)
);