package bkd.src.salus.notificator.Domain.Interface.Application;

public interface ILogMedicine {
    void LogConsume(int userId, int medicineId, String action);
}
