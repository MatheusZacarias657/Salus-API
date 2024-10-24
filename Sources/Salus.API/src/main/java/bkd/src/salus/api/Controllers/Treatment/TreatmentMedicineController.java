package bkd.src.salus.api.Controllers.Treatment;

import bkd.src.salus.api.Application.Service.Tratment.IDayMedicineService;
import bkd.src.salus.api.Domain.DTO.Medicine.MedicineCalendarDetailing;
import bkd.src.salus.api.Domain.DTO.Medicine.MedicineCalendarResponse;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Treatment")
public class TreatmentMedicineController {

    private final ICheckVisibilite checkVisibilite;
    private final IFilterList filterList;
    private final IDayMedicineService dayMedicineService;

    @Autowired
    public TreatmentMedicineController(ICheckVisibilite checkVisibilite, IFilterList filterList, IDayMedicineService dayMedicineService) {
        this.checkVisibilite = checkVisibilite;
        this.filterList = filterList;
        this.dayMedicineService = dayMedicineService;
    }

    @GetMapping("/ByDay")
    public ResponseEntity FindByDay(@RequestHeader("Authorization") String authHeader,
                                    @RequestParam(required = false) Map<String, String> params,
                                    @RequestParam(required = false, defaultValue = "0") Integer userId,
                                    @RequestParam(required = false) String sortBy,
                                    @RequestParam LocalDateTime date){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        MedicineCalendarResponse detailingTreatment = dayMedicineService.FindByDay(id, date);
        List<MedicineCalendarDetailing> filteringMedicines = filterList.ProcessList(detailingTreatment.getMedicines(), params, sortBy);

        return new ResponseEntity<>(new MedicineCalendarResponse(detailingTreatment.getUser(), filteringMedicines), HttpStatus.OK);
    }
}
