package bkd.src.salus.api.Domain.Interface.Application.Medicine;

import bkd.src.salus.api.Domain.Entity.SQL.Medicine.MedicineType;

import java.util.List;

public interface IMedicineTypeService {
    List<MedicineType> ListAll();
}
