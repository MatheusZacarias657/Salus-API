package api.src.salus.api.Domain.DTO.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceResponseDetailing {

    private String UserName;
    private String Typography;
    private boolean EnableStatistics;
}
