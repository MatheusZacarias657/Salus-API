package bkd.src.salus.api.Domain.DTO.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserChangePassword {

    @NotBlank @Email
    private String Password;
}