package api.src.salus.api.Domain.DTO.Treatment;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class RegisterMedicineTreatmentDTO {

    private int MedicineId;
    private float Dosage;
    private float Frequency;
    private LocalDateTime TreatmentEnd;
    private LocalDateTime TreatmentInit;
}
