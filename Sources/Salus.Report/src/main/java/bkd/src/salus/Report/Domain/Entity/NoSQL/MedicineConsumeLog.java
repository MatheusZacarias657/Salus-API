package bkd.src.salus.Report.Domain.Entity.NoSQL;

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
    private TreatmentConsume Treatment;
    private MedicineConsume Medicine;
    private LocalDateTime LogAt;
    private String Action;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class MedicineConsume{
        private String Name;
        private String Importance;
        private String Type;
        private String Dosage;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class TreatmentConsume{
        private String Name;
        private String Importance;

    }
}
