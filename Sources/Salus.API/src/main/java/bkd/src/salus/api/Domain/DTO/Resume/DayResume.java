package bkd.src.salus.api.Domain.DTO.Resume;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayResume {
    private int Total;
    private int Consumed;
    private int Delayed;
    private int Normal;
    List<TreatmentResume> Treatments;
}
