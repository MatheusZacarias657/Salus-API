package api.src.salus.api.Domain.Interface.Application.Medicine;

import api.src.salus.api.Domain.DTO.Medicine.DetailingMedicineDTO;
import api.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import api.src.salus.api.Domain.DTO.Medicine.UpdateMedicineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IMedicineService {
    DetailingMedicineDTO Create(RegisterMedicineDTO register, int userId);

    DetailingMedicineDTO Find(int medicineId, int userId);

    Page<DetailingMedicineDTO> FindAll(int userId, Pageable pageable);

    Map<Integer, String> FindNames(int userId);

    void RemoveMedicine(int medicineId, int userId);

    DetailingMedicineDTO Update(int medicineId, int userId, UpdateMedicineDTO update);
}
