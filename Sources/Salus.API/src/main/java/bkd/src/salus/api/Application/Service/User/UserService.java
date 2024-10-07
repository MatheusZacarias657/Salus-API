package bkd.src.salus.api.Application.Service.User;

import bkd.src.salus.api.Domain.DTO.Auth.TokenResponse;
import bkd.src.salus.api.Domain.DTO.User.UserGenericDTO;
import bkd.src.salus.api.Domain.DTO.User.UserResponse;
import bkd.src.salus.api.Domain.Entity.NoSQL.Topic.MqttTopic;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ITokenGenerate;
import bkd.src.salus.api.Domain.Interface.Application.RabbitMQ.IRabbitCommunicator;
import bkd.src.salus.api.Domain.Interface.Application.User.IAuthUser;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserService;
import bkd.src.salus.api.Repository.NoSQL.Topic.ITopicRepositoryMR;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserService implements IUserService, IAuthUser {

    private final IUserRepositoryJPA userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ITokenGenerate tokenGenerate;
    private final ITopicRepositoryMR topicRepository;
    private final IRabbitCommunicator rabbitCommunicator;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, IUserRepositoryJPA userRepository, ITokenGenerate tokenGenerate, ITopicRepositoryMR topicRepository, IRabbitCommunicator rabbitCommunicator){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenGenerate = tokenGenerate;
        this.topicRepository = topicRepository;
        this.rabbitCommunicator = rabbitCommunicator;
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
        MqttTopic topic = new MqttTopic(entity.getId(), String.format("/notification/user/%d", entity.getId()));
        topicRepository.save(topic);
        rabbitCommunicator.AddSubscriber(List.of(topic.getTopic()));

        return new TokenResponse(token, entity.getLogin(), null);
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
