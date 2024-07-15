package api.src.salus.api.Application.Service.User;

import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.Entity.UserAccount;
import api.src.salus.api.Domain.Interface.Application.User.IAuthUser;
import api.src.salus.api.Domain.Interface.Application.User.IUserService;
import api.src.salus.api.Repository.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService implements IUserService, IAuthUser {

    private IUserRepositoryJPA repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, IUserRepositoryJPA repository){
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Transactional
    public void CreateUser(UserGenericDTO user){
        UserAccount entity = new UserAccount(user);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity = repository.save(entity);

        if (entity.getAnswerable() == null) {
            entity.setAnswerable(entity);
            entity = repository.save(entity);
        }
    }

    @Override
    public UserDetails FindDetailsByLogin(String login) {
        UserAccount userAccount = repository.findByLogin(login);

        return User.withUsername(userAccount.getLogin())
            .password(userAccount.getPassword())
            .authorities("ROLE_USER")
            .accountExpired(!userAccount.isActive())
            .accountLocked(!userAccount.isActive())
            .credentialsExpired(!userAccount.isActive())
            .disabled(!userAccount.isActive())
            .build();
    }
}
