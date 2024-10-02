package bkd.src.salus.api.Domain.DTO.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNotificationDTO {

    private int UserId;
    private int ChannelId;
}
