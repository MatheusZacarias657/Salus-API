package api.src.salus.api.Application.Service.Auth;

import api.src.salus.api.Domain.Interface.Application.User.IAuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private IAuthUser userService;

    @Autowired
    public AuthenticationService(IAuthUser userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.FindDetailsByLogin(username);
    }
}
