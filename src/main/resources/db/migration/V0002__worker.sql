CREATE TABLE worker
(
    id                     INT PRIMARY KEY AUTO_INCREMENT,
    uuid                   VARCHAR(255),
    name                   VARCHAR(255) NOT NULL,
    last_name              VARCHAR(255) NOT NULL,
    pesel                  VARCHAR(255) NOT NULL,
    employment_since       DATE         NOT NULL,
    phone_number           VARCHAR(255) NOT NULL,
    email                  VARCHAR(255) NOT NULL,
    city                   VARCHAR(255) NOT NULL,
    delegation             BIT          NOT NULL DEFAULT 0,
    max_distance_from_city numeric(4, 1)         DEFAULT (0)
);