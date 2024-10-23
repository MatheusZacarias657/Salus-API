package bkd.src.salus.communicator.Domain.DTO.Queue;

import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueItem {
    private int Position;
    private MedicineNotificationRequest Medicine;
}