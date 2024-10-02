package bkd.src.salus.api.Domain.DTO.Patient.Disease;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterPatientDiseaseDTO {

    @NotBlank
    private String Disease;
}
