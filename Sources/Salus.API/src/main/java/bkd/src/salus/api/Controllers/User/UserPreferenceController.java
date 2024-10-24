package bkd.src.salus.api.Controllers.User;

import bkd.src.salus.api.Domain.DTO.User.Preference.UserPreferenceDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserPreferenceController {

    private final IUserPreferenceService preferenceService;
    private final ICheckVisibilite checkVisibility;

    @Autowired
    public UserPreferenceController(IUserPreferenceService preferenceService, ICheckVisibilite checkVisibility){
        this.preferenceService = preferenceService;
        this.checkVisibility = checkVisibility;
    }

    @PutMapping("/Preferences")
    public ResponseEntity RegisterPreferences(@RequestHeader("Authorization") String authHeader, @RequestBody UserPreferenceDTO preferenceDTO){
        int id = checkVisibility.ExtractIdFromToken(authHeader);

        return new ResponseEntity<>(preferenceService.UpdatePreferences(preferenceDTO, id), HttpStatus.OK);
    }

    @GetMapping("/Preferences")
    public ResponseEntity CapturePreferences(@RequestHeader("Authorization") String authHeader){
        int id = checkVisibility.ExtractIdFromToken(authHeader);

        return new ResponseEntity<>(preferenceService.GetPreferences(id), HttpStatus.OK);
    }
}
