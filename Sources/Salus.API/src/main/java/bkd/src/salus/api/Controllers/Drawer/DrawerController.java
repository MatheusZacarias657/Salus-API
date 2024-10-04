package bkd.src.salus.api.Controllers.Drawer;

import bkd.src.salus.api.Domain.DTO.Drawer.RegisterDrawerDTO;
import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.Drawer.IDrawerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Drawer")
public class DrawerController {

    private final IDrawerService drawerService;
    private final ICheckVisibilite checkVisibilite;

    @Autowired
    public DrawerController(IDrawerService drawerService, ICheckVisibilite checkVisibilite) {
        this.drawerService = drawerService;
        this.checkVisibilite = checkVisibilite;
    }

    @PutMapping("/ChangeOwner")
    public ResponseEntity ChangeOwner(){
        //TODO: última prioridade
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity Register(@RequestHeader("Authorization") String authHeader,
                                   @RequestParam(required = false, defaultValue = "0") Integer userId,
                                   @RequestBody RegisterDrawerDTO register){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(drawerService.Register(register, id), HttpStatus.OK);
    }

    @PutMapping("/AddUser")
    public ResponseEntity AddUserOnDrawer(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/List")
    public ResponseEntity FindAll(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
