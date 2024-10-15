CREATE TABLE medicine_picture
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Medicine_Id INT NOT NULL,
    File_Name NVARCHAR(255) NOT NULL,
    File_Data VARBINARY(MAX) NOT NULL

    constraint fk_medicinePicture_user foreign key (Medicine_Id) references medicine(Id)
);