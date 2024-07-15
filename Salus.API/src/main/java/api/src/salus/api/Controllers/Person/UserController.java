package api.src.salus.api.Controllers.Person;

import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import api.src.salus.api.Domain.Interface.Application.Auth.ITokenRead;
import api.src.salus.api.Domain.Interface.Application.User.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {


    private final IUserService userService;
    private final ICheckVisibilite checkVisibilite;

    @Autowired
    public UserController(IUserService userService, ICheckVisibilite checkVisibilite){
        this.userService = userService;
        this.checkVisibilite = checkVisibilite;
    }

    @PostMapping
    public ResponseEntity Create(@RequestBody @Valid UserGenericDTO register){
        userService.CreateUser(register);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity Read(@RequestHeader("Authorization") String authHeader){
        int id = checkVisibilite.ExtractIdFromToken(authHeader);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity Update(@RequestHeader("Authorization") String authHeader){
        int id = checkVisibilite.ExtractIdFromToken(authHeader);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/TurnPro")
    public ResponseEntity TurnPro(@RequestHeader("Authorization") String authHeader){
        int id = checkVisibilite.ExtractIdFromToken(authHeader);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/RegisterAnswerable")
    public ResponseEntity RegisterAnswerable(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity Delete(@RequestHeader("Authorization") String authHeader){
        int id = checkVisibilite.ExtractIdFromToken(authHeader);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
