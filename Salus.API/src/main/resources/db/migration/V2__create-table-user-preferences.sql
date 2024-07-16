CREATE TABLE user_preferences
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    User_Id INT NOT NULL,
    Typography VARCHAR(50) NOT NULL,
    EnableStatistics BIT NOT NULL DEFAULT 0,

    constraint fk_UserId foreign key (User_Id) references user_account(id),
);