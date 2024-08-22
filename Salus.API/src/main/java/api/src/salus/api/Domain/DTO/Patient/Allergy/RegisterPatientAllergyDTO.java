package api.src.salus.api.Domain.DTO.Patient.Allergy;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterPatientAllergyDTO {

    @NotBlank
    private String Allergy;
}
