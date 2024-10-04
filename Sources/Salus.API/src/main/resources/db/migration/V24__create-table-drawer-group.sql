CREATE TABLE drawer_group
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Drawer_Id INT NOT NULL,
    Owner_Id INT NOT NULL,
    User_Id INT NOT NULL,

    constraint fk_drawerGroup_drawer foreign key (Drawer_Id) references drawer(Id),
    constraint fk_drawerGroup_owner foreign key (Owner_Id) references user_account(Id),
    constraint fk_drawerGroup_user foreign key (User_Id) references user_account(Id)
);