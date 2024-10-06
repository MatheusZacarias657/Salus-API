package bkd.src.salus.communicator.Domain.DTO.Notification;

import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationRequest;
import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConsumeMedicineConfirmation extends BaseNotification<MedicineNotificationResponse>{

    public ConsumeMedicineConfirmation(String action, MedicineNotificationResponse notificationResponse){
        super(action, notificationResponse);
    }
}
