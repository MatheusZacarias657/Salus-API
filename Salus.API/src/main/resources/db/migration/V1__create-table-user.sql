CREATE TABLE user_account
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Answerable_Id INT,
    Login VARCHAR(50) NOT NULL,
    Password VARCHAR(200) NOT NULL,
    Is_Pro BIT NOT NULL DEFAULT 0,
    Active BIT NOT NULL DEFAULT 1,

    constraint fk_AnswerableId foreign key (Answerable_Id) references user_account(id),
);