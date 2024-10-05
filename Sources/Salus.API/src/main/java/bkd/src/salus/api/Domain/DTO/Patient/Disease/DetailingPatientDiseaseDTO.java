package bkd.src.salus.api.Domain.DTO.Patient.Disease;

import bkd.src.salus.api.Domain.Entity.SQL.Patient.PatientDisease;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailingPatientDiseaseDTO {
    private int Id;
    private String Disease;

    public DetailingPatientDiseaseDTO(PatientDisease disease){
        this.Id = disease.getId();
        this.Disease = disease.getDisease();
    }

}
