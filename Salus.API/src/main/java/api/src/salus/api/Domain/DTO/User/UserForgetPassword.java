package api.src.salus.api.Domain.DTO.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForgetPassword {

    @NotBlank @Email
    private String login;
}