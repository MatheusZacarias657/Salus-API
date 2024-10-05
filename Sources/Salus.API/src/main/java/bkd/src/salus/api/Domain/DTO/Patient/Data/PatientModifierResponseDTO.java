package bkd.src.salus.api.Domain.DTO.Patient.Data;

import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientModifierResponseDTO {
    private String Name;
    private LocalDateTime Birthdate;
    private String Telephone;
    private String Gender;
    private String Email;

    public PatientModifierResponseDTO(Patient patient){
        this.Name = patient.getName();
        this.Birthdate = patient.getBirthdate();
        this.Telephone = patient.getTelephone();
        this.Gender = patient.getGender();
        this.Email = patient.getUser().getLogin();
    }
}
