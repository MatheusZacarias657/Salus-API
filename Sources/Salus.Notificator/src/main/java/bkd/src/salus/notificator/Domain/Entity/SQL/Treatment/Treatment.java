package bkd.src.salus.notificator.Domain.Entity.SQL.Treatment;

import bkd.src.salus.notificator.Domain.Entity.SQL.Cataloging.Importance;
import bkd.src.salus.notificator.Domain.Entity.SQL.User.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


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
