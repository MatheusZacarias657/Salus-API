package bkd.src.salus.api.Controllers.Medicine;

import bkd.src.salus.api.Domain.DTO.Medicine.DetailingMedicineDTO;
import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.Medicine.IMedicineService;
import bkd.src.salus.api.Domain.Interface.Application.Utils.IFilterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Medicine")
public class MedicineController {

    private final ICheckVisibilite checkVisibility;
    private final IMedicineService medicineService;
    private final IFilterList filterList;

    @Autowired
    public MedicineController(ICheckVisibilite checkVisibility, IMedicineService medicineService, IFilterList filterList){
        this.checkVisibility = checkVisibility;
        this.medicineService = medicineService;
        this.filterList = filterList;
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
                                        @RequestParam(required = false) Map<String, String> params,
                                        @RequestParam(required = false, defaultValue = "0") Integer userId,
                                        @RequestParam(required = false) String sortBy){

        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        List<DetailingMedicineDTO> medicines = medicineService.FindAll(id);
        List<DetailingMedicineDTO> filteringMedicines = filterList.ProcessList(medicines, params, sortBy);

        return new ResponseEntity<>(filteringMedicines, HttpStatus.OK);
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
