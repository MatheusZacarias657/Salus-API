CREATE TABLE patient_allergies
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Patient_Id INT NOT NULL,
    Allergy VARCHAR(100) NOT NULL,

    constraint fk_patient_allergies_PatientId foreign key (Patient_Id) references patient(id)
);