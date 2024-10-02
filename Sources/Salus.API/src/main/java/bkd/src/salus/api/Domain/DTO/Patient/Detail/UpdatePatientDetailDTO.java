package bkd.src.salus.api.Domain.DTO.Patient.Detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePatientDetailDTO {
    private float Height;
    private float Weight;
    private boolean Smoking;
    private boolean Alcohol;
    private boolean Pregnant;
}
