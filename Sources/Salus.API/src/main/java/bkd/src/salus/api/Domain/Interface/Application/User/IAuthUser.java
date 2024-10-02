package bkd.src.salus.api.Domain.Interface.Application.User;

import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthUser {
    UserDetails FindDetailsByLogin(String login);
}
