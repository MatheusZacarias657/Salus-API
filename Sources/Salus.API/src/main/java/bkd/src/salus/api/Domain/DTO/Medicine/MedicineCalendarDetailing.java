package bkd.src.salus.api.Domain.DTO.Medicine;

import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineCalendarDetailing {
    private String TreatmentName;
    private String MedicineName;
    private String Importance;
    private List<String> Pictures;

    public MedicineCalendarDetailing(TreatmentMedicine treatmentMedicine, List<String> pictures){
        this.TreatmentName = treatmentMedicine.getTreatment().getName();
        this.MedicineName = treatmentMedicine.getMedicine().getName();
        this.Importance = treatmentMedicine.getMedicine().getImportance().getName();
        this.Pictures = new ArrayList<>(pictures);
    }
}
