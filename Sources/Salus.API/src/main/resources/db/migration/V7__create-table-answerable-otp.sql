CREATE TABLE answerable_otp
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Otp VARCHAR(6) NOT NULL,
    User_Id INT NOT NULL,

    constraint fk_answerable_otp_UserId foreign key (User_Id) references user_account(id)
);