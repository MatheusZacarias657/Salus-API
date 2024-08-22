package api.src.salus.api.Domain.DTO.Medicine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMedicineDTO {

    private int StorageQuantity;
    private int DrawerNumber;
}
