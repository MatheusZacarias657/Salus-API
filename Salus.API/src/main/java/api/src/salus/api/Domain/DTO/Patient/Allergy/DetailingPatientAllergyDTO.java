package api.src.salus.api.Domain.DTO.Patient.Allergy;

import api.src.salus.api.Domain.Entity.Patient.PatientAllergy;
import api.src.salus.api.Domain.Entity.Patient.PatientDisease;
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
