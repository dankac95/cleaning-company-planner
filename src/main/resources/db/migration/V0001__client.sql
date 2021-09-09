CREATE TABLE client
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    uuid            VARCHAR(255),
    name            VARCHAR(255)   NOT NULL,
    city            VARCHAR(255)   NOT NULL,
    area            DECIMAL(19, 2) NOT NULL,
    price_per_meter DECIMAL(19, 2) NOT NULL
);