package api.src.salus.api.Controllers.User;

import api.src.salus.api.Domain.DTO.Answerable.OtpDTO;
import api.src.salus.api.Domain.Interface.Application.Answerable.IAnswerableService;
import api.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import api.src.salus.api.Domain.Interface.Application.User.IUserService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Answerable")
public class AnswerableController {

    private final IAnswerableService answerableService;
    private final ICheckVisibilite checkVisibilite;

    @Autowired
    public AnswerableController(IAnswerableService answerableService, ICheckVisibilite checkVisibilite){
        this.answerableService = answerableService;
        this.checkVisibilite = checkVisibilite;
    }

    @GetMapping("/Token")
    public ResponseEntity GetToken(@RequestHeader("Authorization") String authHeader){
        int id = checkVisibilite.ExtractIdFromToken(authHeader);

        return new ResponseEntity<>(answerableService.CreateOtp(id), HttpStatus.OK);
    }

    @GetMapping("/Valid")
    public ResponseEntity ValidateBeforeInsert(@RequestParam String otp){
        return new ResponseEntity<>(answerableService.checkBeforeRegister(otp), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity RegisterAnswerable(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid OtpDTO otp){
        int id = checkVisibilite.ExtractIdFromToken(authHeader);
        answerableService.RegisterAnswerable(otp.getOtp(), id);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}