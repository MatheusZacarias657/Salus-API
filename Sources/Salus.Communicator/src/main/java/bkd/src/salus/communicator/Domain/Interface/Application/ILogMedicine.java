package bkd.src.salus.communicator.Domain.Interface.Application;

public interface ILogMedicine {
    void LogConsume(int userId, int medicineId, String action);
}
