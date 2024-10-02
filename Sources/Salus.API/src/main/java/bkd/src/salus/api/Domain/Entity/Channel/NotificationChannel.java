package bkd.src.salus.api.Domain.Entity.Channel;

import bkd.src.salus.api.Domain.DTO.User.UserPreferenceDTO;
import bkd.src.salus.api.Domain.Entity.User.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "notification_channel")
@Entity(name = "NotificationChannel")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class NotificationChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Channel;

}