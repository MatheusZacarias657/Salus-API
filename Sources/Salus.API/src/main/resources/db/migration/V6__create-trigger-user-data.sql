CREATE TRIGGER trg_InsertUserData
ON user_account
AFTER INSERT
AS
BEGIN

    INSERT INTO user_preferences (User_Id, Typography, Enable_Statistics)
    SELECT
        i.Id,
        'Normal',
        0
    FROM
        inserted i;

    INSERT INTO user_notification (User_Id, Channel_Id)
    SELECT
        i.Id,
        (SELECT Id FROM notification_channel WHERE Channel = 'popup')
    FROM
        inserted i;
END;