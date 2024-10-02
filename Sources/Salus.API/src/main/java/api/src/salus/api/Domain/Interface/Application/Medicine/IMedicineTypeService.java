package api.src.salus.api.Domain.Interface.Application.Medicine;

import api.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import api.src.salus.api.Domain.Entity.Medicine.MedicineType;

import java.util.List;

public interface IMedicineTypeService {
    List<MedicineType> ListAll();
}
