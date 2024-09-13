package api.src.salus.api.Domain.Interface.Application.Treatment;

import api.src.salus.api.Domain.DTO.Treatment.DetailingTreatmentMedicineDTO;
import api.src.salus.api.Domain.DTO.Treatment.RegisterMedicineTreatmentDTO;
import api.src.salus.api.Domain.Entity.Treatment.Treatment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITreatmentMedicineService {
    List<DetailingTreatmentMedicineDTO> Register(List<RegisterMedicineTreatmentDTO> registers, Treatment treatment);

    Page<DetailingTreatmentMedicineDTO> FindByTreatmentId(int treatmentId, Pageable pageable);
}
