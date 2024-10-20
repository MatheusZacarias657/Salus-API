package bkd.src.salus.api.Domain.DTO.Treatment;

import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailingTreatmentMedicineDTO {

    private int Id;
    private String Medicine;
    private float Dosage;
    private float Frequency;
    private LocalDateTime TreatmentEnd;
    private LocalDateTime TreatmentInit;

    public DetailingTreatmentMedicineDTO (TreatmentMedicine entity){
        this.Id = entity.getMedicine().getId();
        this.Medicine = entity.getMedicine().getName();
        this.Dosage = entity.getDosage();
        this.Frequency = entity.getFrequency();
        this.TreatmentEnd = entity.getTreatmentEnd();
        this.TreatmentInit = entity.getTreatmentInit();
    }
}
