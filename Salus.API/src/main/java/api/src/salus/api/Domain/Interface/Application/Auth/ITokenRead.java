package api.src.salus.api.Domain.Interface.Application.Auth;

import com.auth0.jwt.exceptions.JWTVerificationException;

public interface ITokenRead {
    String GetSubject(String authHeader) throws JWTVerificationException;
    String GetClaim(String authHeader, String claimName) throws JWTVerificationException;
}
