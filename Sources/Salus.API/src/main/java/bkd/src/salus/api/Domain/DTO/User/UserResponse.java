package bkd.src.salus.api.Domain.DTO.User;

import bkd.src.salus.api.Domain.Entity.User.UserAccount;
import lombok.Data;

@Data
public class UserResponse {
    private String Login;
    private boolean IsPro;

    public UserResponse(UserAccount userAccount){
        this.Login = userAccount.getLogin();
        this.IsPro = userAccount.isIsPro();
    }
}
