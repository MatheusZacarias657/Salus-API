package bkd.src.salus.api.Domain.DTO.Treatment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDetailingTreatment {
    private int MedicineQuantity;
    private LocalDateTime LastEndDate;
    private LocalDateTime FirstInitDate;
    private float TotalPrice;
}
