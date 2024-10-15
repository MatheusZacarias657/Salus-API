package bkd.src.salus.api.Controllers.User;

import bkd.src.salus.api.Domain.DTO.Answerable.AnswarebleUserDTO;
import bkd.src.salus.api.Domain.DTO.Answerable.OtpDTO;
import bkd.src.salus.api.Domain.Interface.Application.Answerable.IAnswerableService;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity ValidateBeforeInsert(@RequestParam String otp, UriComponentsBuilder uriBuilder){
        AnswarebleUserDTO response = answerableService.checkBeforeRegister(otp);
        URI uri = uriBuilder.path("/File/{fileName}").buildAndExpand(response.getPictureName()).toUri();
        response.setProfilePicture(uri.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity RegisterAnswerable(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid OtpDTO otp){
        int id = checkVisibilite.ExtractIdFromToken(authHeader);
        answerableService.RegisterAnswerable(otp.getOtp(), id);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}