package api.src.salus.api.Repository.User;

import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.Entity.User;
import api.src.salus.api.Domain.Interface.Repository.User.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRepository implements IUserRepository, IAuthUserRepository {

    private final UserRepositoryJPA repository;

    @Autowired
    public UserRepository(UserRepositoryJPA repository) {
        this.repository = repository;
    }

    @Transactional
    public User CreateUser(UserGenericDTO user){
        User entity = new User(user);
        repository.save(entity);

        return entity;
    }

    @Override
    public UserDetails FindDetailsByLogin(String login) {
        User user = repository.FindByLogin(login);

        return org.springframework.security.core.userdetails.User.withUsername(user.getLogin())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .accountExpired(user.isActive())
                .accountLocked(user.isActive())
                .credentialsExpired(user.isActive())
                .disabled(user.isActive())
                .build();
    }
}
