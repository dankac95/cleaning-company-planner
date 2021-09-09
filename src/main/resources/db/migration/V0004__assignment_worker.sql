CREATE TABLE assignment_worker(
assignment_id INT,
worker_id INT,
FOREIGN KEY (assignment_id) REFERENCES assignment (id),
FOREIGN KEY (worker_id) REFERENCES worker (id),
PRIMARY KEY (assignment_id, worker_id)
);