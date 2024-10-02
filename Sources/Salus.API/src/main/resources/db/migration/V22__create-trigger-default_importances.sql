CREATE TRIGGER trg_InsertDefaultImportances
ON user_account
AFTER INSERT
AS
BEGIN
    DECLARE @i INT = 1;
    DECLARE @importanceLevels TABLE (Id INT IDENTITY(1,1), Name NVARCHAR(50));

    INSERT INTO @importanceLevels (Name)
    VALUES ('Nenhuma'), ('Baixa'), ('Média'), ('Alta'), ('Crítica');

    WHILE @i <= (SELECT COUNT(*) FROM @importanceLevels)
    BEGIN
        INSERT INTO importance (User_Id, Name)
        SELECT i.Id, l.Name
        FROM inserted i
        CROSS JOIN (SELECT Name FROM @importanceLevels WHERE Id = @i) l;

        -- Increment the counter
        SET @i = @i + 1;
    END
END;