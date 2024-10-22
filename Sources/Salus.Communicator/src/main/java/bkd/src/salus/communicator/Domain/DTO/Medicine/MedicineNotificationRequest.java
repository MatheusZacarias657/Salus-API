package bkd.src.salus.communicator.Domain.DTO.Medicine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicineNotificationRequest {
    private String HardwareId;
    private int UserId;
    private int MedicineId;

    private String UserName;
    private int DrawerNumber;
    private float Quantity;
    private int Type;
}
