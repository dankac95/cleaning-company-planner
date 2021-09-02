CREATE TABLE worker
(
    id                  INT PRIMARY KEY AUTO_INCREMENT,
    uuid                VARCHAR(255),
    name                VARCHAR(255),
    lastName            VARCHAR(255),
    pesel               VARCHAR(255),
    dateOfEmployment    DATETIME,
    phoneNumber         VARCHAR(255),
    emailAdress         VARCHAR(255),
    workerCity          VARCHAR(255),
    maxDistanceFromCity numeric(19, 2)
);