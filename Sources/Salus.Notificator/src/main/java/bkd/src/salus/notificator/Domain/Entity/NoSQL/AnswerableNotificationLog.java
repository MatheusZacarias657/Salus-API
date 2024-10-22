package bkd.src.salus.notificator.Domain.Entity.NoSQL;

import bkd.src.salus.notificator.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.notificator.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.notificator.Domain.Entity.SQL.Treatment.Treatment;
import bkd.src.salus.notificator.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.notificator.Domain.Entity.SQL.User.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "AnswerableNotification_Log")
@TypeAlias("AnswerableNotificationLog")
public class AnswerableNotificationLog {

    @Id
    private String id;

    private UserDetailing Patient;
    private UserDetailing Answerable;
    private String NotificationAt;
    private LocalDateTime LogAt;
    private MedicineData Medicine;
    private String SendText;

    public AnswerableNotificationLog(Patient patient, Patient answerable, TreatmentMedicine treatmentMedicine, String sendText){
        this.Patient = new UserDetailing(patient);
        this.Answerable = new UserDetailing(answerable);
        this.Medicine = new MedicineData(treatmentMedicine.getMedicine(), treatmentMedicine);
        this.LogAt = LocalDateTime.now();
        this.SendText = sendText;
    }

    public AnswerableNotificationLog(UserAccount patient, Patient answerable, TreatmentMedicine treatmentMedicine, String sendText){
        this.Patient = new UserDetailing(patient);
        this.Answerable = new UserDetailing(answerable);
        this.Medicine = new MedicineData(treatmentMedicine.getMedicine(), treatmentMedicine);
        this.LogAt = LocalDateTime.now();
        this.SendText = sendText;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class MedicineData{
        private String Name;
        private String Importance;
        private String Type;
        private float Dosage;

        public MedicineData(Medicine medicine, TreatmentMedicine treatmentMedicine){
            this.Name = medicine.getName();
            this.Importance = medicine.getImportance().getName();
            this.Type = medicine.getType().getName();
            this.Dosage = treatmentMedicine.getDosage();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserDetailing{
        private String Name;
        private String User;

        public UserDetailing(Patient patient){
            this.Name = patient.getName();
            this.User = patient.getUser().getLogin();
        }

        public UserDetailing(UserAccount user){
            this.Name = user.getLogin();
            this.User = user.getLogin();
        }
    }
}
