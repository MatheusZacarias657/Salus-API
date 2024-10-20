package bkd.src.salus.api.Domain.DTO.Treatment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class CompleteDetailingTreatment {
    private DetailingTreatmentDTO Treatment;
    private List<DetailingTreatmentMedicineDTO> Medicines;
}
