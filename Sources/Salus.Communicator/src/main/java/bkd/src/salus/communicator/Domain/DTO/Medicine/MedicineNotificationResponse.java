package bkd.src.salus.communicator.Domain.DTO.Medicine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicineNotificationResponse {
    private String HardwareId;
    private int UserId;
    private int MedicineId;
    private int TreatmentId;
}
