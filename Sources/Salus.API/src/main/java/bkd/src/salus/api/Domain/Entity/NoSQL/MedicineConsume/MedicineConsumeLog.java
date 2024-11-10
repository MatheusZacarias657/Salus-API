package bkd.src.salus.api.Domain.Entity.NoSQL.MedicineConsume;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "MedicineConsume_Log")
@TypeAlias("MedicineConsumeLog")
public class MedicineConsumeLog {

    @Id
    private String id;

    private String Username;
    private String Name;
    private MedicineConsume Medicine;
    private TreatmentConsume Treatment;
    private LocalDateTime LogAt;
    private String Action;

    public MedicineConsumeLog(Patient patient, TreatmentMedicine treatmentMedicine, String action){
        this.Username = patient.getUser().getLogin();
        this.Name = patient.getName();
        this.Medicine = new MedicineConsume(treatmentMedicine.getMedicine(), treatmentMedicine);
        this.Treatment = new TreatmentConsume(treatmentMedicine.getTreatment());
        this.LogAt = LocalDateTime.now();
        this.Action = action;
    }

    public MedicineConsumeLog(UserAccount user, TreatmentMedicine treatmentMedicine, String action){
        this.Username = user.getLogin();
        this.Name = user.getLogin();
        this.Medicine = new MedicineConsume(treatmentMedicine.getMedicine(), treatmentMedicine);
        this.Treatment = new TreatmentConsume(treatmentMedicine.getTreatment());
        this.LogAt = LocalDateTime.now();
        this.Action = action;
    }
}
