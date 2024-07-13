package api.src.salus.api.Controllers.Drawer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Own/Drawer")
public class OwnDrawerController {

    @GetMapping("/CheckDrawerIsAvailable")
    public ResponseEntity Read(@PathVariable int id){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity Create(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/AddUserOnDrawer")
    public ResponseEntity AddUserOnDrawer(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
