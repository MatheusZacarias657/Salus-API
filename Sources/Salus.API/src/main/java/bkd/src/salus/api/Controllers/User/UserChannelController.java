package bkd.src.salus.api.Controllers.User;

import bkd.src.salus.api.Domain.DTO.User.Notification.UserNotificationRequestDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserChannelController {

    private final ICheckVisibilite checkVisibility;
    private final IUserNotificationService notificationService;

    @Autowired
    public UserChannelController(ICheckVisibilite checkVisibility, IUserNotificationService notificationService){
        this.checkVisibility = checkVisibility;
        this.notificationService = notificationService;
    }

    @PostMapping("/Channel")
    public ResponseEntity RegisterNotification(@RequestHeader("Authorization") String authHeader,@RequestBody UserNotificationRequestDTO userNotificationRequest){
        return new ResponseEntity<>(notificationService.RegisterNotification(userNotificationRequest.getChannelId(), checkVisibility.ExtractIdFromToken(authHeader)), HttpStatus.CREATED);
    }

    //TODO: Deleção correção
    @DeleteMapping("/Channel/{channelId}")
    public ResponseEntity DeleteNotification(@RequestHeader("Authorization") String authHeader, @PathVariable Integer channelId){
        notificationService.DeleteNotification(channelId, checkVisibility.ExtractIdFromToken(authHeader));
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/Channel")
    public ResponseEntity CaptureNotification(@RequestHeader("Authorization") String authHeader){
        return new ResponseEntity<>(notificationService.CaptureAll(checkVisibility.ExtractIdFromToken(authHeader)), HttpStatus.CREATED);
    }
}
