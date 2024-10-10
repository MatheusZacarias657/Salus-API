package bkd.src.salus.notificator.Domain.Entity.Treatment;

import bkd.src.salus.notificator.Domain.Entity.Cataloging.Importance;
import bkd.src.salus.notificator.Domain.Entity.User.UserAccount;
import jakarta.persistence.*;
import lombok.*;


@Table(name = "treatment")
@Entity(name = "Treatment")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Id")
    private UserAccount User;

    private String Name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Importance_Id")
    private Importance Importance;

    private boolean Finished;
}
