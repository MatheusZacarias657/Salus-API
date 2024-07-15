package api.src.salus.api.Domain.Entity;

import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_account")
@Entity(name = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String Login;
    private String Password;

    @Column(name = "Is_Pro")
    private boolean IsPro;
    private boolean Active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Answerable_Id")
    private UserAccount Answerable;

    public UserAccount(UserGenericDTO user){
        this.Login = user.getLogin();
        this.IsPro = false;
        this.Active = true;
        this.Id = 0;
    }

    @PostPersist
    public void setAnswerableAfterPersist() {
        if (this.Answerable == null) {
            this.Answerable = this;
        }
    }
}