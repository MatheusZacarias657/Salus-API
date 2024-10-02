package bkd.src.salus.api.Domain.Interface.Application.User;

import bkd.src.salus.api.Domain.DTO.Auth.TokenResponse;
import bkd.src.salus.api.Domain.DTO.User.UserGenericDTO;
import bkd.src.salus.api.Domain.DTO.User.UserResponse;

public interface IUserService {
    TokenResponse CreateUser(UserGenericDTO user);
    UserResponse ReadUser(int id);
    UserResponse TurnPro(int id);
    void DeleteUser(int id);
}
