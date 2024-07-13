package api.src.salus.api.Domain.Interface.Application.Auth;

import api.src.salus.api.Domain.DTO.Auth.TokenResponse;
import api.src.salus.api.Domain.DTO.User.UserForgetPassword;
import api.src.salus.api.Domain.DTO.User.UserGenericDTO;

public interface IOAuthService {
    TokenResponse Login(UserGenericDTO login) throws Exception;

    boolean Logout(int id);

    boolean ForgetPassword(UserForgetPassword user);
}
