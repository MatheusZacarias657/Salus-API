package bkd.src.salus.api.Domain.DTO.Medicine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMedicineDTO {

    @NotBlank
    private String Name;

    @NotBlank
    private String Type;

    @NotBlank
    private String UnitType;

    @NotBlank
    private int StorageQuantity;

    @NotBlank
    private LocalDateTime ExpirationDate;

    @NotBlank
    private String Importance;

    private float Price;
    private int DrawerNumber;
    private String HardwareId;
}
