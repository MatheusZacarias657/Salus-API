package bkd.src.salus.api.Controllers.User;

import bkd.src.salus.api.Domain.DTO.User.UserGenericDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {

    private final IUserService userService;
    private final ICheckVisibilite checkVisibility;

    @Autowired
    public UserController(IUserService userService, ICheckVisibilite checkVisibility){
        this.userService = userService;
        this.checkVisibility = checkVisibility;
    }

    @PostMapping
    public ResponseEntity Create(@RequestBody @Valid UserGenericDTO register){
        return new ResponseEntity<>(userService.CreateUser(register), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity Read(@RequestHeader("Authorization") String authHeader){
        int id = checkVisibility.ExtractIdFromToken(authHeader);

        return new ResponseEntity<>(userService.ReadUser(id), HttpStatus.OK);
    }

    @PutMapping("/TurnPro")
    public ResponseEntity TurnPro(@RequestHeader("Authorization") String authHeader){
        int id = checkVisibility.ExtractIdFromToken(authHeader);

        return new ResponseEntity<>(userService.TurnPro(id), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity Delete(@RequestHeader("Authorization") String authHeader){
        int id = checkVisibility.ExtractIdFromToken(authHeader);
        userService.DeleteUser(id);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
