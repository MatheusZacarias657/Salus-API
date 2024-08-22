package api.src.salus.api.Domain.Interface.Application.Medicine;

import api.src.salus.api.Domain.DTO.Medicine.DetailingMedicineDTO;
import api.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import api.src.salus.api.Domain.DTO.Medicine.UpdateMedicineDTO;

import java.util.List;

public interface IMedicineService {
    DetailingMedicineDTO Create(RegisterMedicineDTO register, int userId);

    DetailingMedicineDTO Find(int medicineId, int userId);

    List<DetailingMedicineDTO> FindAll(int userId);

    List<String> FindNames(int userId);

    void RemoveMedicine(int medicineId, int userId);

    DetailingMedicineDTO Update(int medicineId, int userId, UpdateMedicineDTO update);
}
