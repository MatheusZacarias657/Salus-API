CREATE TABLE patient_address
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Patient_Id INT NOT NULL,
    Zipcode VARCHAR(8) NOT NULL,
    Street VARCHAR(100) NOT NULL,
    Neighborhood VARCHAR(50) NOT NULL,
    City VARCHAR(30) NOT NULL,
    State VARCHAR(30) NOT NULL,

    constraint fk_patient_address_PatientId foreign key (Patient_Id) references patient(id)
);