package bkd.src.salus.api.Domain.Entity.NoSQL.BuyingMedicineLog;

import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "BuyingMedicine_Log")
@TypeAlias("BuyingMedicineLog")
public class BuyingMedicineLog {

    @Id
    private String id;

    private String Username;
    private String Name;
    private BuyingMedicine Medicine;
    private LocalDateTime LogAt;

    public BuyingMedicineLog (Medicine medicine, Patient patient){
        this.Username = patient.getUser().getLogin();
        this.Name = patient.getName();
        this.Medicine = new BuyingMedicine(medicine);
        this.LogAt = LocalDateTime.now();
    }

    public BuyingMedicineLog (Medicine medicine, UserAccount user){
        this.Username = user.getLogin();
        this.Name = user.getLogin();
        this.Medicine = new BuyingMedicine(medicine);
        this.LogAt = LocalDateTime.now();
    }
}
