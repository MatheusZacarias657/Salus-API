package api.src.salus.api.Domain.DTO.Patient.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPatientDataDTO {
    @NotBlank
    private String Name;

    @NotNull
    private LocalDateTime Birthdate;

    @NotBlank @Pattern(regexp = "^55[1-9][1-9]9\\d{4}\\d{4}$")
    private String Telephone;

    @NotBlank
    private String Gender;


}
