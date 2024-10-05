package bkd.src.salus.api.Domain.Entity.SQL.Treatment;

import bkd.src.salus.api.Domain.DTO.Treatment.RegisterMedicineTreatmentDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "treatment_medicine")
@Entity(name = "TreatmentMedicine")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class TreatmentMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Treatment_Id")
    private Treatment Treatment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Medicine_Id")
    private Medicine Medicine;

    private float Dosage;
    private float Frequency;

    @Column(name = "Treatment_End")
    private LocalDateTime TreatmentEnd;

    @Column(name = "Treatment_Init")
    private LocalDateTime TreatmentInit;

    private boolean Finished;

    public TreatmentMedicine(RegisterMedicineTreatmentDTO register, Medicine medicine, Treatment treatment){
        this.Treatment = treatment;
        this.Medicine = medicine;
        this.Dosage = register.getDosage();
        this.Frequency = register.getFrequency();
        this.TreatmentEnd = register.getTreatmentEnd();
        this.TreatmentInit = register.getTreatmentInit();
        this.Finished = false;
    }
    
    //oi matheuzito :D s2
}
