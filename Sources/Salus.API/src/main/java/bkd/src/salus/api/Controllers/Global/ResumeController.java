package bkd.src.salus.api.Controllers.Global;

import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.Drawer.IDrawerResumeService;
import bkd.src.salus.api.Domain.Interface.Application.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Resume")
public class ResumeController {

    private final IResumeService resumeService;
    private final IDrawerResumeService drawerResumeService;
    private final ICheckVisibilite checkVisibility;

    //TODO: validar duplicações

    @Autowired
    public ResumeController(IResumeService resumeService, IDrawerResumeService drawerResumeService, ICheckVisibilite checkVisibility) {
        this.resumeService = resumeService;
        this.drawerResumeService = drawerResumeService;
        this.checkVisibility = checkVisibility;
    }

    @GetMapping("/HasPendencies")
    public ResponseEntity HasPendencies(@RequestHeader("Authorization") String authHeader,
                                        @RequestParam(required = false, defaultValue = "0") Integer userId){
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(resumeService.CheckIfHasPendency(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity GetResume(@RequestHeader("Authorization") String authHeader,
                                    @RequestParam(required = false, defaultValue = "0") Integer userId){
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(resumeService.GetResumeOfDay(id), HttpStatus.OK);
    }

    @GetMapping("/DrawerStatus")
    public ResponseEntity GetDrawerResume(@RequestHeader("Authorization") String authHeader,
                                          @RequestParam(required = false, defaultValue = "0") Integer userId){
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(drawerResumeService.CaptureStatus(id), HttpStatus.OK);
    }
}
