CREATE TABLE importance
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    User_Id INT NOT NULL,
    Name VARCHAR(80) NOT NULL,

    constraint fk_importance_UserId foreign key (User_Id) references user_account(id)
);