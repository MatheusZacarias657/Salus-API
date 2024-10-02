package bkd.src.salus.api.Domain.DTO.Treatment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CompleteRegisterTreatment {
    private RegisterTreatmentDTO Treatment;
    private List<RegisterMedicineTreatmentDTO> Medicines;
}
