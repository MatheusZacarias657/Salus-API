package bkd.src.salus.api.Domain.DTO.Resume;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  MedicineResume {
    private String Name;
    private String Status;
}
