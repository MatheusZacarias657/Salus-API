package api.src.salus.api.Domain.Interface.Application.Auth;

import api.src.salus.api.Domain.Entity.User;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;

public interface ITokenGenerate {
    String GenerateToken(User user) throws JWTCreationException;
}
