package api.src.salus.api.Domain.DTO.Patient.Detail;

import api.src.salus.api.Domain.Entity.Patient.PatientDetail;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDetailModifierResponseDTO {
    private float Height;
    private float Weight;
    private boolean Smoking;
    private boolean Alcohol;
    private boolean Pregnant;
    private String Email;

    public PatientDetailModifierResponseDTO(PatientDetail patientDetail){
        this.Height = patientDetail.getHeight();
        this.Weight = patientDetail.getWeight();
        this.Smoking = patientDetail.isSmoking();
        this.Alcohol = patientDetail.isAlcohol();
        this.Pregnant = patientDetail.isPregnant();
        this.Email = patientDetail.getPatient().getUser().getLogin();
    }

    public void Update(UpdatePatientDetailDTO updatePatientDetailDTO){
        //TODO: implementar o update
    }
}
