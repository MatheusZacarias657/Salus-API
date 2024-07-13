package api.src.salus.api.Domain.DTO.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserGenericDTO {

    @NotBlank @Email
    private String login;

    @NotBlank
    private String password;
}