package bkd.src.salus.communicator.Domain.DTO.Medicine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicineDecrementRequest {
    private int UserId;
    private int MedicineId;
    private float Quantity;
}
