package bkd.src.salus.api.Domain.DTO.Patient.Allergy;

import bkd.src.salus.api.Domain.Entity.SQL.Patient.PatientAllergy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailingPatientAllergyDTO {
    private int Id;
    private String Allergy;

    public DetailingPatientAllergyDTO(PatientAllergy disease){
        this.Id = disease.getId();
        this.Allergy = disease.getAllergy();
    }

}
