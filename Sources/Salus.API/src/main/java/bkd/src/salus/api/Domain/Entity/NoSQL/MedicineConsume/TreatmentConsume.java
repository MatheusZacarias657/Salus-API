package bkd.src.salus.api.Domain.Entity.NoSQL.MedicineConsume;

import bkd.src.salus.api.Domain.Entity.SQL.Treatment.Treatment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentConsume{
    private String Name;
    private String Importance;

    public TreatmentConsume(Treatment treatment){
        this.Name = treatment.getName();
        this.Importance = treatment.getImportance().getName();
    }
}
