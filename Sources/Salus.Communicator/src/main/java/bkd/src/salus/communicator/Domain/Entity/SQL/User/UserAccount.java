package bkd.src.salus.communicator.Domain.Entity.SQL.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user_account")
@Entity(name = "User")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(unique = true)
    private String Login;
    private String Password;

    @Column(name = "Is_Pro")
    private boolean IsPro;
    private boolean Active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Answerable_Id")
    private UserAccount Answerable;

//    @Override
//    public String toString() {
//        return "UserAccount: " + "Id:" + this.Id + "| Login:" + this.Login;
//    }
}