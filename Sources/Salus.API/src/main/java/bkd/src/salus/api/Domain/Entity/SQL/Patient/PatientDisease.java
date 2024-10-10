package bkd.src.salus.api.Domain.Entity.SQL.Patient;


import bkd.src.salus.api.Domain.DTO.Patient.Disease.RegisterPatientDiseaseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "patient_diseases")
@Entity(name = "PatientDisease")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class PatientDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Patient_Id")
    private Patient Patient;

    private String Disease;

    public PatientDisease(RegisterPatientDiseaseDTO patientDetail, Patient patient){
        this.Patient = patient;
        this.Disease = patientDetail.getDisease();
    }
}
