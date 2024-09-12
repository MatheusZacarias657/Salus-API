CREATE TABLE patient
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    User_Id INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Birthdate DATE NOT NULL,
    Telephone VARCHAR(15) NOT NULL,
    Gender VARCHAR(30) NOT NULL,

    constraint fk_patient_UserId foreign key (User_Id) references user_account(id)
);