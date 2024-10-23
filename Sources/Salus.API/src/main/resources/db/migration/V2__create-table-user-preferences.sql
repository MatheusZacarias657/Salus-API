CREATE TABLE user_preferences
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    User_Id INT NOT NULL,
    Typography VARCHAR(50) NOT NULL,
    Enable_Statistics BIT NOT NULL DEFAULT 0,

    constraint fk_user_preferences_UserId foreign key (User_Id) references user_account(id),
);