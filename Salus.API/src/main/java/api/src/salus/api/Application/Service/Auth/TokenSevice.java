package api.src.salus.api.Application.Service.Auth;

import api.src.salus.api.Application.Utils.TimeTools;
import api.src.salus.api.Domain.Entity.UserAccount;
import api.src.salus.api.Domain.Interface.Application.Auth.ITokenGenerate;
import api.src.salus.api.Domain.Interface.Application.Auth.ITokenRead;
import api.src.salus.api.Repository.User.IUserRepositoryJPA;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAmount;

@Service
public class TokenSevice implements ITokenGenerate, ITokenRead {

    @Value("${api.security.token.secret}")
    private String secretKey;

    @Value("${api.security.token.expiration}")
    private String expirationTime;

    @Value("${spring.application.name}")
    private String applicationName;

    private IUserRepositoryJPA repository;

    @Autowired
    public TokenSevice(IUserRepositoryJPA repository){
        this.repository = repository;
    }

    @Override
    public String GenerateToken(User user) throws JWTCreationException {
        UserAccount userAccount = repository.findByLogin(user.getUsername());
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withIssuer(applicationName)
                .withSubject(userAccount.getLogin())
                .withClaim("id", String.valueOf(userAccount.getId()))
                .withExpiresAt(expirationDate())
                .sign(algorithm);
    }

    @Override
    public String GetSubject(String authHeader) throws JWTVerificationException {
        String token = GetToken(authHeader);

        if(token != null){
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return  JWT.require(algorithm)
                    .withIssuer(applicationName)
                    .build()
                    .verify(token)
                    .getSubject();
        }

        return null;
    }

    @Override
    public String GetClaim(String authHeader, String claimName) throws JWTVerificationException {

        String token = GetToken(authHeader);

        if(token != null){
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer(applicationName)
                    .build()
                    .verify(token);

            return decodedJWT.getClaim(claimName).asString();
        }

        return null;
    }

    private String GetToken(String authHeader) {
        if (authHeader != null){
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }

    private Instant expirationDate() {

        ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(java.time.Instant.now());
        TemporalAmount expiration = TimeTools.convertStringToTemporalAmount(expirationTime);
        return LocalDateTime.now().plus(expiration).toInstant(zoneOffset);
    }
}
