package bkd.src.salus.api.Controllers.User;

import bkd.src.salus.api.Domain.DTO.User.UserNotificationDTO;
import bkd.src.salus.api.Domain.DTO.User.UserNotificationRequestDTO;
import bkd.src.salus.api.Domain.DTO.User.UserPreferenceDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserNotificationService;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserComplementController {

    private final IUserPreferenceService preferenceService;
    private final ICheckVisibilite checkVisibility;
    private final IUserNotificationService notificationService;

    @Autowired
    public UserComplementController(IUserPreferenceService preferenceService, ICheckVisibilite checkVisibility, IUserNotificationService notificationService){
        this.preferenceService = preferenceService;
        this.checkVisibility = checkVisibility;
        this.notificationService = notificationService;
    }

    @PutMapping("/Preferences")
    public ResponseEntity RegisterPreferences(@RequestHeader("Authorization") String authHeader, @RequestBody UserPreferenceDTO preferenceDTO){
        int id = checkVisibility.ExtractIdFromToken(authHeader);

        return new ResponseEntity<>(preferenceService.UpdatePreferences(preferenceDTO, id), HttpStatus.OK);
    }

    @PostMapping("/Notification")
    public ResponseEntity RegisterNotification(@RequestHeader("Authorization") String authHeader,@RequestBody UserNotificationRequestDTO userNotificationRequest){
        UserNotificationDTO notification = new UserNotificationDTO(checkVisibility.ExtractIdFromToken(authHeader), userNotificationRequest.getChannelId());
        notificationService.RegisterNotification(notification);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/Notification")
    public ResponseEntity DeleteNotification(@RequestHeader("Authorization") String authHeader,@RequestBody UserNotificationRequestDTO userNotificationRequest){
        UserNotificationDTO notification = new UserNotificationDTO(checkVisibility.ExtractIdFromToken(authHeader), userNotificationRequest.getChannelId());
        notificationService.DeleteNotification(notification);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
