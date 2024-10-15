CREATE TABLE profile_picture
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    User_Id INT NOT NULL,
    File_Name NVARCHAR(255) NOT NULL,
    File_Data VARBINARY(MAX) NOT NULL

    constraint fk_profilePicture_user foreign key (User_Id) references user_account(Id)
);