package api.src.salus.api.Application.Service.Auth;

import api.src.salus.api.Domain.DTO.Auth.TokenResponse;
import api.src.salus.api.Domain.DTO.Auth.UserForgetPassword;
import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.Interface.Application.Auth.IOAuthService;
import api.src.salus.api.Domain.Interface.Application.Auth.ITokenGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class OAuthService implements IOAuthService {

    private final AuthenticationManager manager;
    private final ITokenGenerate tokenSevice;

    @Autowired
    public OAuthService(AuthenticationManager manager, ITokenGenerate tokenSevice){
        this.manager = manager;
        this.tokenSevice = tokenSevice;
    }

    @Override
    public TokenResponse Login(UserGenericDTO login) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());
        Authentication auth = manager.authenticate(authenticationToken);
        String token = tokenSevice.GenerateToken((User) auth.getPrincipal());

        return new TokenResponse(token);
    }

    @Override
    public boolean Logout(int id){

        return true;
    }

    @Override
    public boolean ForgetPassword(UserForgetPassword user){

        return true;
    }
}
