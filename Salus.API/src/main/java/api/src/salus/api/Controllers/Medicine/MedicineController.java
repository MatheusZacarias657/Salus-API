package api.src.salus.api.Controllers.Medicine;

import api.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import api.src.salus.api.Domain.DTO.Medicine.UpdateMedicineDTO;
import api.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import api.src.salus.api.Domain.Interface.Application.Medicine.IMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Medicine")
public class MedicineController {

    private final ICheckVisibilite checkVisibilite;
    private final IMedicineService medicineService;

    @Autowired
    public MedicineController(ICheckVisibilite checkVisibilite, IMedicineService medicineService){
        this.checkVisibilite = checkVisibilite;
        this.medicineService = medicineService;
    }

    @PostMapping("")
    public ResponseEntity Create(@RequestHeader("Authorization") String authHeader,
                                 @RequestParam(required = false) int userId,
                                 @RequestBody RegisterMedicineDTO register){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicineService.Create(register, id), HttpStatus.OK);
    }

    @GetMapping("/{medicineId}")
    public ResponseEntity ReadById(@RequestHeader("Authorization") String authHeader,
                                   @PathVariable int medicineId,
                                   @RequestParam(required = false) int userId){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicineService.Find(medicineId, id), HttpStatus.OK);
    }

    @GetMapping("/List")
    public ResponseEntity ListMedicines(@RequestHeader("Authorization") String authHeader,
                                        @RequestParam(required = false) int userId,
                                        @PageableDefault(size = 10, sort = {"name"}) Pageable pageable){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicineService.FindAll(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/ListNames")
    public ResponseEntity ListNames(@RequestHeader("Authorization") String authHeader,
                                    @RequestParam(required = false) int userId){

        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicineService.FindNames(id), HttpStatus.OK);
    }

    @PutMapping("/{medicineId}")
    public ResponseEntity Update(@RequestHeader("Authorization") String authHeader,
                                 @PathVariable int medicineId,
                                 @RequestBody UpdateMedicineDTO update,
                                 @RequestParam(required = false) int userId){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicineService.Update(medicineId, id, update), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{medicineId}")
    public ResponseEntity Delete(@RequestHeader("Authorization") String authHeader,
                                 @PathVariable int medicineId,
                                 @RequestParam(required = false) int userId){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        medicineService.RemoveMedicine(medicineId, id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
