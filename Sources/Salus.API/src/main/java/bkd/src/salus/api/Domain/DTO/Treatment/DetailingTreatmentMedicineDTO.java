package bkd.src.salus.api.Domain.DTO.Treatment;

import bkd.src.salus.api.Domain.Entity.Treatment.TreatmentMedicine;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailingTreatmentMedicineDTO {

    public String Medicine;
    private float Dosage;
    private float Frequency;
    private LocalDateTime TreatmentEnd;
    private LocalDateTime TreatmentInit;

    public DetailingTreatmentMedicineDTO (TreatmentMedicine entity){
        this.Medicine = entity.getMedicine().getName();
        this.Dosage = entity.getDosage();
        this.Frequency = entity.getFrequency();
        this.TreatmentEnd = entity.getTreatmentEnd();
        this.TreatmentInit = entity.getTreatmentInit();
    }
}
