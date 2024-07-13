package api.src.salus.api.Domain.Interface.Repository.User;

import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.Entity.User;

public interface IUserRepository {
    User CreateUser(UserGenericDTO user);
}
