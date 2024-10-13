package bkd.src.salus.communicator.Domain.Entity.SQL.Treatment;

import jakarta.persistence.*;
import lombok.*;
import bkd.src.salus.communicator.Domain.Entity.SQL.Medicine.Medicine;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Treatment_Id")
    private Treatment Treatment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Medicine_Id")
    private Medicine Medicine;

    private float Dosage;
    private float Frequency;

    @Column(name = "Treatment_End")
    private LocalDateTime TreatmentEnd;

    @Column(name = "Treatment_Init")
    private LocalDateTime TreatmentInit;

    private boolean Finished;

    public void FinishTreatment(){
        this.Finished = true;
    }
}

