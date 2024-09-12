package api.src.salus.api.Domain.DTO.Patient.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPatientAddressDTO {

    @NotBlank @Pattern(regexp = "(^\\d{8}$)|(^\\d{5}-\\d{3}$)|(^\\d{2}.\\d{3}-\\d{3}$)")
    private String Zipcode;

    @NotBlank
    private String Street;

    @NotBlank
    private String Neighborhood;

    @NotBlank
    private String City;

    @NotBlank
    private String State;
}
