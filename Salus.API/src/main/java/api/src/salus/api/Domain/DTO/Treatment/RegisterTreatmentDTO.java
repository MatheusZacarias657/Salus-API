package api.src.salus.api.Domain.DTO.Treatment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterTreatmentDTO {
    private String Name;
    private String Importance;
}
