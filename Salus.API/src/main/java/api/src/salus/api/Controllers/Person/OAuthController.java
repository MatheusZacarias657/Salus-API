package api.src.salus.api.Controllers.Person;

import api.src.salus.api.Domain.DTO.User.UserForgetPassword;
import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.Interface.Application.Auth.IOAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/OAuth")
public class OAuthController {

    private final IOAuthService authService;

    public OAuthController(IOAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/Login")
    public ResponseEntity Login(@RequestBody @Valid UserGenericDTO login) throws Exception {
        return new ResponseEntity<>(authService.Login(login), HttpStatus.OK);
    }

    @PostMapping("/Logout/{id}")
    public ResponseEntity Logout(@PathVariable int id){
        return new ResponseEntity<>(authService.Logout(id), HttpStatus.OK);
    }

    @PostMapping("/ForgotPassword")
    public ResponseEntity Login(@RequestBody @Valid UserForgetPassword user){
        return new ResponseEntity<>(authService.ForgetPassword(user), HttpStatus.OK);
    }
}
