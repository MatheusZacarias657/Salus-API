package bkd.src.salus.api.Controllers.Global;

import bkd.src.salus.api.Domain.DTO.Importance.DetailingImportanceDTO;
import bkd.src.salus.api.Domain.DTO.Importance.RegisterImportanceDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.IImportanceService;
import bkd.src.salus.api.Domain.Interface.Application.Utils.IFilterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Importance")
public class ImportanceController {

    private final ICheckVisibilite checkVisibility;
    private final IImportanceService importanceService;
    private final IFilterList filterList;

    @Autowired
    public ImportanceController(ICheckVisibilite checkVisibility, IImportanceService importanceService, IFilterList filterList) {
        this.checkVisibility = checkVisibility;
        this.importanceService = importanceService;
        this.filterList = filterList;
    }

    @GetMapping("")
    public ResponseEntity ListAll(@RequestHeader("Authorization") String authHeader,
                                  @RequestParam(required = false) Map<String, String> params,
                                  @RequestParam(required = false, defaultValue = "0") Integer userId,
                                  @RequestParam(required = false) String sortBy){

        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        List<DetailingImportanceDTO> importance = importanceService.FindAll(id);
        List<DetailingImportanceDTO> filteringImportance = filterList.ProcessList(importance, params, sortBy);

        return new ResponseEntity<>(filteringImportance, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity Register(@RequestHeader("Authorization") String authHeader,
                                   @RequestParam(required = false, defaultValue = "0") Integer userId,
                                   @RequestBody RegisterImportanceDTO register){
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(importanceService.Create(register, id), HttpStatus.OK);
    }

    @DeleteMapping("/{importanceId}")
    public ResponseEntity Delete(@PathVariable int importanceId,
                                 @RequestHeader("Authorization") String authHeader,
                                 @RequestParam(required = false, defaultValue = "0") Integer userId){
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        importanceService.RemoveMedicine(importanceId, id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
