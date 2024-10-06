CREATE TABLE drawer
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Hardware_Id VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Number_of_Drawers INT NOT NULL
);