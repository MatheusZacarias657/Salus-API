package bkd.src.salus.communicator.Domain.DTO.Notification;

import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsumeMedicineNotification extends BaseNotification<MedicineNotificationRequest>{

    public ConsumeMedicineNotification(String action, MedicineNotificationRequest notificationRequest){
        super(action, notificationRequest);
    }
}
