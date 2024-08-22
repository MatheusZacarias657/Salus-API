package api.src.salus.api.Domain.Entity.Treatment;

import api.src.salus.api.Domain.Entity.Cataloging.Importance;
import api.src.salus.api.Domain.Entity.Medicine.Medicine;
import api.src.salus.api.Domain.Entity.User.UserAccount;
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
}
