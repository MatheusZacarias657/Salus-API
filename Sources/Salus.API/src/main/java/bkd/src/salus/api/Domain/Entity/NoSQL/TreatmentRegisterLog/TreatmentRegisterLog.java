package bkd.src.salus.api.Domain.Entity.NoSQL.TreatmentRegisterLog;

import bkd.src.salus.api.Domain.Entity.NoSQL.BuyingMedicineLog.BuyingMedicine;
import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.Treatment;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "TreatmentRegister_Log")
@TypeAlias("TreatmentRegisterLog")
public class TreatmentRegisterLog {

    @Id
    private String id;

    private String Username;
    private String Name;

    private String TreatmentName;
    private String Importance;

    private List<TreatmentMedicineRegister> Medicines;
    private LocalDateTime LogAt;

    public TreatmentRegisterLog (Treatment treatment, Patient patient){
        this.Username = patient.getUser().getLogin();
        this.Name = patient.getName();
        this.TreatmentName = treatment.getName();
        this.Importance = treatment.getImportance().getName();
        this.LogAt = LocalDateTime.now();
    }

    public TreatmentRegisterLog (Treatment treatment, UserAccount user){
        this.Username = user.getLogin();
        this.Name = user.getLogin();
        this.TreatmentName = treatment.getName();
        this.Importance = treatment.getImportance().getName();
        this.LogAt = LocalDateTime.now();
    }

    public void AddMedicine(List<TreatmentMedicineRegister> treatmentMedicines){
        this.Medicines = new ArrayList<>(treatmentMedicines);
    }
}
