CREATE OR ALTER TRIGGER trg_SetTreatmentEnd
ON treatment_medicine
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Update rows in the table where Treatment_End is NULL
    UPDATE treatment_medicine
    SET Treatment_End = CAST('9999-12-31 23:59:59.9999999' AS DATETIME2)
    FROM treatment_medicine tm
    INNER JOIN inserted i
        ON tm.Id = i.Id
    WHERE tm.Treatment_End IS NULL;
END;
GO