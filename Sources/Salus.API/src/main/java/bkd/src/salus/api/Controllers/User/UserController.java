package bkd.src.salus.api.Controllers.User;

import bkd.src.salus.api.Domain.DTO.User.UserGenericDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.User.IProfilePictureService;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/User")
public class UserController {

    private final IUserService userService;
    private final ICheckVisibilite checkVisibility;
    private final IProfilePictureService profilePictureService;

    @Autowired
    public UserController(IUserService userService, ICheckVisibilite checkVisibility, IProfilePictureService profilePictureService){
        this.userService = userService;
        this.checkVisibility = checkVisibility;
        this.profilePictureService = profilePictureService;
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

    @PostMapping("/ProfilePicture")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file,
                                     @RequestHeader("Authorization") String authHeader,
                                     UriComponentsBuilder uriBuilder) throws Exception {
        int id = checkVisibility.ExtractIdFromToken(authHeader);
        String fileName = profilePictureService.SaveImage(file, id);
        URI uri = UriComponentsBuilder.fromPath("/File/{fileName}").buildAndExpand(fileName).toUri();
        Map<String, Object> respose = new HashMap<>();
        respose.put("availableOn", uri);

        return new ResponseEntity<>(respose, HttpStatus.CREATED);
    }
}
