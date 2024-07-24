package api.src.salus.api.Domain.DTO.Patient.Address;

import api.src.salus.api.Domain.Entity.Patient.PatientAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientAddressModifierResponseDTO {

    private String Zipcode;
    private String Street;
    private String Neighborhood;
    private String City;
    private String State;
    private String Email;

    public PatientAddressModifierResponseDTO(PatientAddress patientAddress){
        this.Zipcode = patientAddress.getZipcode();
        this.Street = patientAddress.getStreet();
        this.Neighborhood = patientAddress.getNeighborhood();
        this.City = patientAddress.getCity();
        this.State = patientAddress.getState();
        this.Email = patientAddress.getPatient().getUser().getLogin();
    }
}
