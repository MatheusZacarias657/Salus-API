package api.src.salus.api.Application.Service.User;

import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.Interface.Repository.User.IUserRepository;
import api.src.salus.api.Domain.Interface.Application.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserRepository repository;

    @Autowired
    public UserService(IUserRepository repository){
        this.repository = repository;
    }

    public void CreateUser(UserGenericDTO user){
        repository.CreateUser(user);
    }
}
