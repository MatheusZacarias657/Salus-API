package api.src.salus.api.Controllers;

import api.src.salus.api.Domain.DTO.Auth.UserChangePassword;
import api.src.salus.api.Domain.DTO.Auth.UserForgetPassword;
import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import api.src.salus.api.Domain.Interface.Application.Auth.IOAuthService;
import api.src.salus.api.Domain.Interface.Application.User.IUserPasswordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/OAuth")
public class OAuthController {

    private final IOAuthService authService;
    private final ICheckVisibilite checkVisibility;
    private final IUserPasswordService userPasswordService;

    @Autowired
    public OAuthController(IOAuthService authService, ICheckVisibilite checkVisibility, IUserPasswordService userPasswordService) {
        this.authService = authService;
        this.checkVisibility = checkVisibility;
        this.userPasswordService = userPasswordService;
    }

    @PostMapping("/Login")
    public ResponseEntity Login(@RequestBody @Valid UserGenericDTO login) throws Exception {
        return new ResponseEntity<>(authService.Login(login), HttpStatus.OK);
    }

    @PostMapping("/ForgotPassword")
    public ResponseEntity Login(@RequestBody @Valid UserForgetPassword user) throws Exception{
        Map<String, Object> respose = new HashMap<>();
        respose.put("sendEmail", authService.ForgetPassword(user));

        return new ResponseEntity<>(respose, HttpStatus.ACCEPTED);
    }

    @PostMapping("/ChangePassword")
    public ResponseEntity ChangePassword(@RequestHeader("Authorization") String authHeader, @RequestBody UserChangePassword changePassword){
        int userId = checkVisibility.ExtractIdFromToken(authHeader);
        userPasswordService.ChangePassword(userId, changePassword.getPassword());

        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }
}
