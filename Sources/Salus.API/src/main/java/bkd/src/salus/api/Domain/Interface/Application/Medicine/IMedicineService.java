package bkd.src.salus.api.Domain.Interface.Application.Medicine;

import bkd.src.salus.api.Domain.DTO.Medicine.DetailingMedicineDTO;
import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
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
}
