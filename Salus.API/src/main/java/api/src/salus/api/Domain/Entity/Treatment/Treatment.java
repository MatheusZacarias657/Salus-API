package api.src.salus.api.Domain.Entity.Treatment;

import api.src.salus.api.Domain.Entity.Cataloging.Importance;
import api.src.salus.api.Domain.Entity.User.UserAccount;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private UserAccount User;

    private String Name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Importance_Id")
    private Importance Importance;

    private boolean Removed;
}
