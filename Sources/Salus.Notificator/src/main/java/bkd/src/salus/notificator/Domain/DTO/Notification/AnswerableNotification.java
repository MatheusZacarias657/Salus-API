package bkd.src.salus.notificator.Domain.DTO.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerableNotification {
    private int UserId;
    private int MedicineId;
}
