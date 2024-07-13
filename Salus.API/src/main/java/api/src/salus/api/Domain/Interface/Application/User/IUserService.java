package api.src.salus.api.Domain.Interface.Application.User;

import api.src.salus.api.Domain.DTO.User.UserGenericDTO;

public interface IUserService {
    void CreateUser(UserGenericDTO user);
}
