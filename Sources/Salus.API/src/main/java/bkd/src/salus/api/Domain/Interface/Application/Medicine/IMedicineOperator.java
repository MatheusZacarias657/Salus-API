package bkd.src.salus.api.Domain.Interface.Application.Medicine;

public interface IMedicineOperator {
    void DecrementMedicine(int medicineId, int userId, int quantity);
}
