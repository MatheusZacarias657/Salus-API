package bkd.src.salus.api.Domain.Entity.SQL.User;

import bkd.src.salus.api.Domain.Entity.SQL.Channel.NotificationChannel;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Id")
    private UserAccount User;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Channel_Id")
    private NotificationChannel Channel;

    public UserNotification(UserAccount user, NotificationChannel channel){
        this.User = user;
        this.Channel = channel;
    }
}