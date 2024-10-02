package bkd.src.salus.api.Domain.DTO.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {

    private String token;
    private String userLogin;
    private String userName;
}
