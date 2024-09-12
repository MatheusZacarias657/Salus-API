CREATE TABLE patient_diseases
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Patient_Id INT NOT NULL,
    Disease VARCHAR(100) NOT NULL,

    constraint fk_patient_diseases_PatientId foreign key (Patient_Id) references patient(id)
);