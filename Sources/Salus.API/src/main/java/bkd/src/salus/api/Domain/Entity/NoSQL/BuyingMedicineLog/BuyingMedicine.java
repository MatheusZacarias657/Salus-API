package bkd.src.salus.api.Domain.Entity.NoSQL.BuyingMedicineLog;

import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyingMedicine {
    private float Price;
    private String Name;
    private int Quantity;
    private String Type;
    private String Importance;

    public BuyingMedicine(Medicine medicine){
        this.Price = medicine.getPrice();
        this.Name = medicine.getName();
        this.Quantity = medicine.getStorageQuantity();
        this.Type = medicine.getType().getName();
        this.Importance = medicine.getImportance().getName();
    }
}
