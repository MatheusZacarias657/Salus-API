CREATE TABLE patient_detail
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Patient_Id INT,
    Height FLOAT NOT NULL,
    Weight FLOAT NOT NULL,
    Smoking BIT NOT NULL,
    Alcohol BIT NOT NULL,
    Pregnant BIT NOT NULL,

    constraint fk_patient_detail_PatientId foreign key (Patient_Id) references patient(id)
);