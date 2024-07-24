package api.src.salus.api.Domain.DTO.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNotificationResponseDetailing {

    private String UserName;
    private String ChannelId;
    private boolean EnableStatistics;
}
