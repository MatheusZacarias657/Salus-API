package bkd.src.salus.api.Domain.Entity.Patient;


import bkd.src.salus.api.Domain.DTO.Patient.Allergy.RegisterPatientAllergyDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Disease.RegisterPatientDiseaseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "patient_allergies")
@Entity(name = "PatientAllergy")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class PatientAllergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Patient_Id")
    private Patient Patient;

    private String Allergy;

    public PatientAllergy(RegisterPatientAllergyDTO patientDetail, Patient patient){
        this.Patient = patient;
        this.Allergy = patientDetail.getAllergy();
    }
}
