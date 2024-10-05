package bkd.src.salus.api.Application.Service.User;

import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserPasswordService;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPasswordService implements IUserPasswordService {

    private IUserRepositoryJPA userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserPasswordService(PasswordEncoder passwordEncoder, IUserRepositoryJPA userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public void ChangePassword(int userId, String newPassword){
        UserAccount entity = userRepository.getReferenceById(userId);
        entity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(entity);
    }
}
