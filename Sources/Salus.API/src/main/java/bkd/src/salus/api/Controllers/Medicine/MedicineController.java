package bkd.src.salus.api.Controllers.Medicine;

import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.Medicine.IMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Medicine")
public class MedicineController {

    private final ICheckVisibilite checkVisibility;
    private final IMedicineService medicineService;

    @Autowired
    public MedicineController(ICheckVisibilite checkVisibility, IMedicineService medicineService){
        this.checkVisibility = checkVisibility;
        this.medicineService = medicineService;
    }

    @PostMapping("")
    public ResponseEntity Create(@RequestHeader("Authorization") String authHeader,
                                 @RequestParam(required = false, defaultValue = "0") Integer userId,
                                 @RequestBody RegisterMedicineDTO register){
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicineService.Create(register, id), HttpStatus.OK);
    }

    @GetMapping("/{medicineId}")
    public ResponseEntity ReadById(@RequestHeader("Authorization") String authHeader,
                                   @PathVariable int medicineId,
                                   @RequestParam(required = false, defaultValue = "0") Integer userId){
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicineService.Find(medicineId, id), HttpStatus.OK);
    }

    @GetMapping("/List")
    public ResponseEntity ListMedicines(@RequestHeader("Authorization") String authHeader,
                                        @RequestParam(required = false, defaultValue = "0") Integer userId,
                                        @PageableDefault(size = 10, sort = {"Name"}) Pageable pageable){
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicineService.FindAll(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/ListNames")
    public ResponseEntity ListNames(@RequestHeader("Authorization") String authHeader,
                                    @RequestParam(required = false,defaultValue = "0") Integer userId){

        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicineService.FindNames(id), HttpStatus.OK);
    }

    @DeleteMapping("/{medicineId}")
    public ResponseEntity Delete(@RequestHeader("Authorization") String authHeader,
                                 @PathVariable int medicineId,
                                 @RequestParam(required = false,defaultValue = "0") Integer userId){
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        medicineService.RemoveMedicine(medicineId, id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
