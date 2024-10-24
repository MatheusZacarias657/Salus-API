package bkd.src.salus.api.Domain.DTO.User.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChannelResponse {
    private String UserName;
    private List<ChannelResponseDetailing> Channels;
}
