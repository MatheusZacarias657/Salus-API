package bkd.src.salus.api.Domain.DTO.Medicine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineCalendarResponse {
    private String User;
    private List<MedicineCalendarDetailing> Medicines;
}
