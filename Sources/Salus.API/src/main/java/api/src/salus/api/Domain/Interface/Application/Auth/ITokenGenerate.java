package api.src.salus.api.Domain.Interface.Application.Auth;

import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.security.core.userdetails.User;

public interface ITokenGenerate {
    String GenerateToken(User user) throws JWTCreationException;
}
