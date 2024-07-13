CREATE TABLE Users
(
    ID INT PRIMARY KEY IDENTITY(1,1),
    AnswerableId INT NOT NULL,
    Login VARCHAR(50) NOT NULL,
    Password VARCHAR(200) NOT NULL,
    IsPro BIT NOT NULL DEFAULT 0,
    Active BIT NOT NULL DEFAULT 1,

    constraint fk_AnswerableId foreign key (AnswerableId) references Users(id),
);