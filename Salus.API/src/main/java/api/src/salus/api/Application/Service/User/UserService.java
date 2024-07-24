package api.src.salus.api.Application.Service.User;

import api.src.salus.api.Domain.DTO.Auth.TokenResponse;
import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.DTO.User.UserResponse;
import api.src.salus.api.Domain.Entity.User.UserAccount;
import api.src.salus.api.Domain.Interface.Application.Auth.ITokenGenerate;
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

    private IUserRepositoryJPA userRepository;
    private PasswordEncoder passwordEncoder;
    private ITokenGenerate tokenGenerate;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, IUserRepositoryJPA userRepository, ITokenGenerate tokenGenerate){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenGenerate = tokenGenerate;
    }

    @Transactional
    public TokenResponse CreateUser(UserGenericDTO user){
        UserAccount entity = new UserAccount(user);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity = userRepository.save(entity);

        if (entity.getAnswerable() == null) {
            entity.setAnswerable(entity);
            userRepository.save(entity);
        }

        String token = tokenGenerate.GenerateToken((User) ConvertToUserDetails(entity));

        return new TokenResponse(token);
    }

    public UserResponse ReadUser(int id){
        UserAccount entity = userRepository.getReferenceById(id);

        return new UserResponse(entity);
    }

    @Transactional
    public UserResponse TurnPro(int id){
        UserAccount entity = userRepository.getReferenceById(id);
        entity.setIsPro(true);

        return new UserResponse(entity);
    }

    @Transactional
    public void DeleteUser(int id){
        UserAccount entity = userRepository.getReferenceById(id);
        entity.setActive(false);
    }

    @Override
    public UserDetails FindDetailsByLogin(String login) {
        UserAccount userAccount = userRepository.findByLogin(login);

        return ConvertToUserDetails(userAccount);
    }

    private UserDetails ConvertToUserDetails(UserAccount userAccount){
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
