package api.src.salus.api.Controllers.Treatment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Treatment")
public class TreatmentController {

//    @PostMapping("")
//    public ResponseEntity Create(){
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity ReadById(@PathVariable int id){
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }
//
//    @GetMapping("/List")
//    public ResponseEntity ListTreatments(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable){
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity Update(@PathVariable int id){
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity Delete(@PathVariable int id){
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }
}
