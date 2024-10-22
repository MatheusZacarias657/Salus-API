package bkd.src.salus.communicator.Domain.DTO.Medicine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerableNotification {
    private int UserId;
    private int MedicineId;
}
