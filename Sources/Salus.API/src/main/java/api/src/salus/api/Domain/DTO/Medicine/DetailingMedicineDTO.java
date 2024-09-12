package api.src.salus.api.Domain.DTO.Medicine;

import api.src.salus.api.Domain.Entity.Medicine.Medicine;
import api.src.salus.api.Domain.Entity.Patient.PatientAllergy;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailingMedicineDTO {
    private int Id;
    private String Name;
    private String Type;
    private String UnitType;
    private int StorageQuantity;
    private LocalDateTime ExpirationDate;
    private String Importance;
    private int DrawerNumber;

    public DetailingMedicineDTO(Medicine medicine){
        this.Name = medicine.getName();
        this.Type = medicine.getType().getName();
        this.UnitType = medicine.getUnitType().getName();
        this.StorageQuantity = medicine.getStorageQuantity();
        this.ExpirationDate = medicine.getExpirationDate();
        this.Importance = medicine.getImportance().getName();
        this.DrawerNumber = medicine.getDrawerNumber();
        this.Id = medicine.getId();
    }

}
