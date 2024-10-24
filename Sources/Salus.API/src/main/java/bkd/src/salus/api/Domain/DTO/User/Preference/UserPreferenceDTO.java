package bkd.src.salus.api.Domain.DTO.User.Preference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceDTO {
    private String Typography;
    private boolean EnableStatistics;
}
