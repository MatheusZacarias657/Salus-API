package api.src.salus.api.Domain.Entity.User;

import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "user_account")
@Entity(name = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
@SQLRestriction("Active = true")
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