CREATE TABLE user_notification
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    User_Id INT NOT NULL,
    Channel_Id INT NOT NULL,

    constraint fk_UserId foreign key (User_Id) references user_account(id),
    constraint fk_ChannelId foreign key (Channel_Id) references notification_channel(id),
);