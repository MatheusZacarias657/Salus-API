package api.src.salus.api.Controllers.Medicine;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Medicine")
public class MediceController {

    @PostMapping("")
    public ResponseEntity Create(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/{medicineId}")
    public ResponseEntity ReadById(@PathVariable int medicineId, @RequestParam(required = false) int userId){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/List")
    public ResponseEntity ListMedicines(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/ListNames")
    public ResponseEntity ListNames(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity Update(@PathVariable int id){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity Delete(@PathVariable int id){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
