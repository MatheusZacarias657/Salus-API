package api.src.salus.api.Controllers.Person;

import api.src.salus.api.Domain.DTO.User.UserGenericDTO;
import api.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import api.src.salus.api.Domain.Interface.Application.User.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class AnswerableController {

    private final IUserService userService;
    private final ICheckVisibilite checkVisibilite;

    @Autowired
    public AnswerableController(IUserService userService, ICheckVisibilite checkVisibilite){
        this.userService = userService;
        this.checkVisibilite = checkVisibilite;
    }

    @PutMapping("/RegisterAnswerable")
    public ResponseEntity RegisterAnswerable(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
