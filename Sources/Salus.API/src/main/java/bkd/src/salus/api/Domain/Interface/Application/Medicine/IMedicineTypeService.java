package bkd.src.salus.api.Domain.Interface.Application.Medicine;

import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import bkd.src.salus.api.Domain.Entity.Medicine.MedicineType;

import java.util.List;

public interface IMedicineTypeService {
    List<MedicineType> ListAll();
}
