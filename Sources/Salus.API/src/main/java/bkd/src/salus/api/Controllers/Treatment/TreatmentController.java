package bkd.src.salus.api.Controllers.Treatment;

import bkd.src.salus.api.Domain.DTO.Medicine.DetailingMedicineDTO;
import bkd.src.salus.api.Domain.DTO.Treatment.CompleteDetailingTreatment;
import bkd.src.salus.api.Domain.DTO.Treatment.CompleteRegisterTreatment;
import bkd.src.salus.api.Domain.DTO.Treatment.DetailingTreatmentDTO;
import bkd.src.salus.api.Domain.DTO.Treatment.DetailingTreatmentMedicineDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.Treatment.ITreatmentService;
import bkd.src.salus.api.Domain.Interface.Application.Utils.IFilterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Treatment")
public class TreatmentController {

    private final ITreatmentService treatmentService;
    private final ICheckVisibilite checkVisibilite;
    private final IFilterList filterList;

    @Autowired
    public TreatmentController(ITreatmentService treatmentService, ICheckVisibilite checkVisibilite, IFilterList filterList) {
        this.treatmentService = treatmentService;
        this.checkVisibilite = checkVisibilite;
        this.filterList = filterList;
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
                                         @RequestParam(required = false) Map<String, String> params,
                                         @RequestParam(required = false, defaultValue = "0") Integer userId,
                                         @RequestParam(required = false) String sortBy){

        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        List<DetailingTreatmentDTO> treatments = treatmentService.FindAll(id);
        List<DetailingTreatmentDTO> filteringTreatments = filterList.ProcessList(treatments, params, sortBy);

        return new ResponseEntity<>(filteringTreatments, HttpStatus.OK);
    }

    @GetMapping("/{treatmentId}")
    public ResponseEntity ReadById(@PathVariable int treatmentId,
                                   @RequestHeader("Authorization") String authHeader,
                                   @RequestParam(required = false) Map<String, String> params,
                                   @RequestParam(required = false, defaultValue = "0") Integer userId,
                                   @RequestParam(required = false) String sortBy){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        CompleteDetailingTreatment detailingTreatment = treatmentService.Find(treatmentId, id);
        List<DetailingTreatmentMedicineDTO> filteringMedicines = filterList.ProcessList(detailingTreatment.getMedicines(), params, sortBy);

        return new ResponseEntity<>(new CompleteDetailingTreatment(detailingTreatment.getTreatment(), filteringMedicines), HttpStatus.OK);
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
