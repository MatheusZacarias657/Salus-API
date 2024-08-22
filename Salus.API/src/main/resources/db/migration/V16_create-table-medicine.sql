CREATE TABLE medicine
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    User_Id INT NOT NULL,
    Name VARCHAR(150) NOT NULL,
    Type_Id INT NOT NULL,
    Unit_Id INT NOT NULL,
    Storage_Quantity INT NOT NULL,
    Expiration_Date DATETIME NOT NULL,
    Importance_Id INT NOT NULL,
    Removed BIT NOT NULL DEFAULT 0,
    Drawer_Number INT NOT NULL,

    constraint fk_medicine_UserId foreign key (User_Id) references user_account(id),
    constraint fk_medicine_TypeId foreign key (Type_Id) references medicine_type(id),
    constraint fk_medicine_UnitId foreign key (Unit_Id) references medicine_unit_type(id),
    constraint fk_medicine_ImportanceId foreign key (Importance_Id) references importance(id)
);