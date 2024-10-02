package bkd.src.salus.api.Domain.Interface.Application.Auth;

import bkd.src.salus.api.Domain.DTO.Auth.TokenResponse;
import bkd.src.salus.api.Domain.DTO.Auth.UserForgetPassword;
import bkd.src.salus.api.Domain.DTO.User.UserGenericDTO;

public interface IOAuthService {
    TokenResponse Login(UserGenericDTO login) throws Exception;

    boolean ForgetPassword(UserForgetPassword user) throws Exception;
}
