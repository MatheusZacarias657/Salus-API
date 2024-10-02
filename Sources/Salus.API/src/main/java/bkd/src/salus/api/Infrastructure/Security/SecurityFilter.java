package bkd.src.salus.api.Infrastructure.Security;

import bkd.src.salus.api.Domain.Interface.Application.Auth.ITokenRead;
import bkd.src.salus.api.Domain.Interface.Application.User.IAuthUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final ITokenRead tokenService;
    private final IAuthUser authUserService;

    @Autowired
    public SecurityFilter(ITokenRead tokenService, IAuthUser authUserService) {
        this.tokenService = tokenService;
        this.authUserService = authUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String subject = tokenService.GetSubject(authHeader);

        if (subject != null){
            UserDetails user = authUserService.FindDetailsByLogin(subject);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
