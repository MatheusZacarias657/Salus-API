package api.src.salus.api.Domain.Interface.Application.User;

import api.src.salus.api.Domain.DTO.Auth.TokenResponse;
import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.DTO.User.UserResponse;

public interface IUserService {
    TokenResponse CreateUser(UserGenericDTO user);
    UserResponse ReadUser(int id);
    UserResponse TurnPro(int id);
    void DeleteUser(int id);
}
