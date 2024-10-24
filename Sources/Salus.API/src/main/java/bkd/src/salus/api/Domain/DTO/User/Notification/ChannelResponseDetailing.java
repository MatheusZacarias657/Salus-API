package bkd.src.salus.api.Domain.DTO.User.Notification;

import bkd.src.salus.api.Domain.Entity.SQL.Channel.NotificationChannel;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserNotification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelResponseDetailing {
    private int Id;
    private String Name;

    public ChannelResponseDetailing(UserNotification notification){
        this.Id = notification.getId();
        this.Name = notification.getChannel().getChannel();
    }
}
