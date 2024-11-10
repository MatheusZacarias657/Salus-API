package bkd.src.salus.api.Domain.Entity.NoSQL.MedicineConsume;

import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineConsume{
    private String Name;
    private String Importance;
    private String Type;
    private float Dosage;

    public MedicineConsume(Medicine medicine, TreatmentMedicine treatmentMedicine){
        this.Name = medicine.getName();
        this.Importance = medicine.getImportance().getName();
        this.Type = medicine.getType().getName();
        this.Dosage = treatmentMedicine.getDosage();
    }

}
