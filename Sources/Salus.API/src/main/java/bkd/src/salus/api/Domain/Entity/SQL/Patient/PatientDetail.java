package bkd.src.salus.api.Domain.Entity.SQL.Patient;


import bkd.src.salus.api.Domain.DTO.Patient.Detail.RegisterPatientDetailDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Detail.UpdatePatientDetailDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "patient_detail")
@Entity(name = "PatientDetail")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class PatientDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Patient_Id")
    private Patient Patient;

    private float Height;
    private float Weight;
    private boolean Smoking;
    private boolean Alcohol;
    private boolean Pregnant;

    public PatientDetail(RegisterPatientDetailDTO patientDetail, Patient patient){
        this.Patient = patient;
        this.Height = patientDetail.getHeight();
        this.Weight = patientDetail.getWeight();
        this.Smoking = patientDetail.isSmoking();
        this.Alcohol = patientDetail.isAlcohol();
        this.Pregnant = patientDetail.isPregnant();
    }

    public void Update(UpdatePatientDetailDTO patientDetail){
        this.Height = (patientDetail.getHeight() != 0) ? patientDetail.getHeight() : this.Height;
        this.Weight = (patientDetail.getWeight() != 0) ? patientDetail.getWeight() : this.Weight;
    }
}
