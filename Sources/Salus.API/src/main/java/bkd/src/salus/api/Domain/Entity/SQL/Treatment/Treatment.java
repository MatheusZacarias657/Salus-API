package bkd.src.salus.api.Domain.Entity.SQL.Treatment;

import bkd.src.salus.api.Domain.Entity.SQL.Cataloging.Importance;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "treatment")
@Entity(name = "Treatment")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
@SQLRestriction("Finished = false")
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

    public Treatment(String name, UserAccount user, Importance importance){
        this.User = user;
        this.Name = name;
        this.Importance = importance;
        this.Finished = false;
    }

    public void Finish(){
        this.Finished = true;
    }
}
