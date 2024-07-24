package api.src.salus.api.Domain.DTO.Patient.Detail;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPatientDetailDTO {

    @NotNull
    private float Height;

    @NotNull
    private float Weight;

    private boolean Smoking;
    private boolean Alcohol;
    private boolean Pregnant;

}
