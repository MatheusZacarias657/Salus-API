CREATE TABLE treatment
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    User_Id INT NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Importance_Id INT NOT NULL,
    Finished INT NOT NULL,

    constraint fk_treatment_UserId foreign key (User_Id) references user_account(id),
    constraint fk_treatment_ImportanceId foreign key (Importance_Id) references importance(id)
);