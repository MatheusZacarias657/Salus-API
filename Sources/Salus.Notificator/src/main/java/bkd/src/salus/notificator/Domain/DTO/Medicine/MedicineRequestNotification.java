package bkd.src.salus.notificator.Domain.DTO.Medicine;

import bkd.src.salus.notificator.Domain.Entity.Treatment.Treatment;
import bkd.src.salus.notificator.Domain.Entity.Treatment.TreatmentMedicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRequestNotification {
    private TreatmentMedicine treatmentMedicine;
    private Treatment treatment;
}
