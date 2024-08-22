package api.src.salus.api.Controllers.Treatment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Treatment")
public class TreatmentMedicineController {

    @PostMapping("/Medicine")
    public ResponseEntity RegisterMedicine(){
        //medicamento-tratamento
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/Medicine/{medicineId}")
    public ResponseEntity DeleteMedicine(@PathVariable int medicineId){
        //medicamento-tratamento
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
