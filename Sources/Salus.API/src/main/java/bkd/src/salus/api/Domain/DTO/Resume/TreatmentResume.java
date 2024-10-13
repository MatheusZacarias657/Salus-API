package bkd.src.salus.api.Domain.DTO.Resume;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentResume {
    private String Name;
    private List<MedicineResume> Medicines;
}
