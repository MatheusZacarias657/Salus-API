package bkd.src.salus.api.Domain.Entity.NoSQL.BuyingMedicineLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
