package bkd.src.salus.notificator.Domain.DTO.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicineNotificationRequest {
    private String HardwareId;
    private int UserId;
    private int DrawerNumber;
    private String UserName;
    private int MedicineId;
    private float Quantity;
    private int Type; //Comprido 1 Gotas 0
    private int TreatmentId;
}
