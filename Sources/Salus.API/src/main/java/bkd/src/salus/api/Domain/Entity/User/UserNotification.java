package bkd.src.salus.api.Domain.Entity.User;

import bkd.src.salus.api.Domain.DTO.User.UserNotificationDTO;
import bkd.src.salus.api.Domain.DTO.User.UserPreferenceDTO;
import bkd.src.salus.api.Domain.Entity.Channel.NotificationChannel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "user_notification", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "channel_id"})
})
@Entity(name = "UserNotification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "User_Id")
    private int UserId;

    @Column(name = "Channel_Id", nullable = false)
    private int ChannelId;

    public UserNotification(UserNotificationDTO userNotification){
        this.ChannelId = userNotification.getChannelId();
        this.UserId = userNotification.getUserId();
    }
}