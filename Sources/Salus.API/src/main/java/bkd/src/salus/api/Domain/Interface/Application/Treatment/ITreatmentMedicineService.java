package bkd.src.salus.api.Domain.Interface.Application.Treatment;

import bkd.src.salus.api.Domain.DTO.Treatment.DetailingTreatmentMedicineDTO;
import bkd.src.salus.api.Domain.DTO.Treatment.RegisterMedicineTreatmentDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.Treatment;

import java.util.List;

public interface ITreatmentMedicineService {
    List<DetailingTreatmentMedicineDTO> Register(List<RegisterMedicineTreatmentDTO> registers, Treatment treatment);

    List<DetailingTreatmentMedicineDTO> FindByTreatmentId(int treatmentId);
}
