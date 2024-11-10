package bkd.src.salus.api.Domain.DTO.Resume;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentResume {
    private String Name;
    private List<MedicineResume> Medicines;

    public TreatmentResume(String name){
        this.Name = name;
        this.Medicines = new ArrayList<>();
    }

    public void AddMedicine (MedicineResume medicineResume){
        this.Medicines.add(medicineResume);
    }
}
