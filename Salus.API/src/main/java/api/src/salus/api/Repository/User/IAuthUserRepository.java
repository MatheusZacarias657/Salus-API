package api.src.salus.api.Repository.User;

import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthUserRepository {
    UserDetails FindDetailsByLogin(String login);
}
