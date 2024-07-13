package api.src.salus.api.Domain.Entity;

import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "Users")
@Entity(name = "User")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "ID")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String Login;
    private String Password;
    private boolean IsPro;
    private boolean Active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AnswerableId")
    private User Answerable;

    public User(UserGenericDTO register){
        this.Login = register.getLogin();
        this.Password = register.getPassword();
    }
}
