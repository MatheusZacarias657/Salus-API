package bkd.src.salus.api.Application.Service.Auth;

import bkd.src.salus.api.Application.Utils.TimeTools;
import bkd.src.salus.api.Domain.Entity.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ITokenGenerate;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ITokenRead;
import bkd.src.salus.api.Repository.User.IUserRepositoryJPA;
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
}
