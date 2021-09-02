CREATE TABLE client
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    uuid       VARCHAR(255),
    name       VARCHAR(255),
    city       VARCHAR(255),
    area       DECIMAL(19, 2),
    pricePerM2 DECIMAL (19, 2) NOT NULL
);