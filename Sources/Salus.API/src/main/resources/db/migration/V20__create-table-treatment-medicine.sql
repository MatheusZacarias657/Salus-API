CREATE TABLE treatment_medicine
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Treatment_Id INT NOT NULL,
    Medicine_Id INT NOT NULL,
    Dosage INT NOT NULL,
    Frequency FLOAT NOT NULL,
    Treatment_End DATETIME NULL,
    Treatment_Init DATETIME NOT NULL,
    Finished BIT NOT NULL,

    constraint fk_treatmentMedicine_treatment foreign key (Treatment_Id) references treatment(id),
    constraint fk_treatmentMedicine_medicine foreign key (Medicine_Id) references medicine(Id)
);