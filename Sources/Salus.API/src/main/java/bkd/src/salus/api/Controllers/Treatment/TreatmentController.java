package bkd.src.salus.api.Controllers.Treatment;

import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import bkd.src.salus.api.Domain.DTO.Treatment.CompleteRegisterTreatment;
import bkd.src.salus.api.Domain.DTO.Treatment.RegisterTreatmentDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.Treatment.ITreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Treatment")
public class TreatmentController {

    private final ITreatmentService treatmentService;
    private final ICheckVisibilite checkVisibilite;

    @Autowired
    public TreatmentController(ITreatmentService treatmentService, ICheckVisibilite checkVisibilite) {
        this.treatmentService = treatmentService;
        this.checkVisibilite = checkVisibilite;
    }

    @PostMapping("")
    public ResponseEntity Create(@RequestHeader("Authorization") String authHeader,
                                 @RequestParam(required = false, defaultValue = "0") Integer userId,
                                 @RequestBody CompleteRegisterTreatment register){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(treatmentService.Create(register, id), HttpStatus.OK);
    }

    @GetMapping("/List")
    public ResponseEntity ListTreatments(@RequestHeader("Authorization") String authHeader,
                                         @RequestParam(required = false, defaultValue = "0") Integer userId,
                                         @PageableDefault(size = 10, sort = {"Name"}) Pageable pageable){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(treatmentService.FindAll(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/{treatmentId}")
    public ResponseEntity ReadById(@PathVariable int treatmentId,
                                   @RequestHeader("Authorization") String authHeader,
                                   @RequestParam(required = false, defaultValue = "0") Integer userId,
                                   @PageableDefault(size = 10) Pageable pageable){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(treatmentService.Find(treatmentId, id, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity Delete(@PathVariable int treatmentId,
                                 @RequestHeader("Authorization") String authHeader,
                                 @RequestParam(required = false, defaultValue = "0") Integer userId){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        treatmentService.Delete(treatmentId, id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
