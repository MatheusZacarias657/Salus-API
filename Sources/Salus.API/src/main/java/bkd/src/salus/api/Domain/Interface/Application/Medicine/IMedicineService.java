package bkd.src.salus.api.Domain.Interface.Application.Medicine;

import bkd.src.salus.api.Domain.DTO.Medicine.DetailingMedicineDTO;
import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;

import java.util.List;
import java.util.Map;

public interface IMedicineService {
    DetailingMedicineDTO Create(RegisterMedicineDTO register, int userId);

    DetailingMedicineDTO Find(int medicineId, int userId);

    List<DetailingMedicineDTO> FindAll(int userId);

    Map<Integer, String> FindNames(int userId);

    void RemoveMedicine(int medicineId, int userId);
}
