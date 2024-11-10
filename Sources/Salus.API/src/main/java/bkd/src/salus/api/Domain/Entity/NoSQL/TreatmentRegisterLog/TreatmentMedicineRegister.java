package bkd.src.salus.api.Domain.Entity.NoSQL.TreatmentRegisterLog;

import bkd.src.salus.api.Domain.Entity.NoSQL.BuyingMedicineLog.BuyingMedicine;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentMedicineRegister {
    private BuyingMedicine Medicine;
    private float Frequency;
    private float Dosage;

    public TreatmentMedicineRegister (TreatmentMedicine treatmentMedicine){
        this.Medicine = new BuyingMedicine(treatmentMedicine.getMedicine());
        this.Frequency = treatmentMedicine.getFrequency();
        this.Dosage = treatmentMedicine.getDosage();
    }
}
