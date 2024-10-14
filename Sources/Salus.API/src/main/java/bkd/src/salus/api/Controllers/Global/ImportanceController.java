package bkd.src.salus.api.Controllers.Global;

import bkd.src.salus.api.Domain.DTO.Importance.RegisterImportanceDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.IImportanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Importance")
public class ImportanceController {

    private final ICheckVisibilite checkVisibilite;
    private final IImportanceService importanceService;

    @Autowired
    public ImportanceController(ICheckVisibilite checkVisibilite, IImportanceService importanceService) {
        this.checkVisibilite = checkVisibilite;
        this.importanceService = importanceService;
    }

    @GetMapping("")
    public ResponseEntity ListAll(@RequestHeader("Authorization") String authHeader,
                                  @RequestParam(required = false, defaultValue = "0") Integer userId,
                                  @PageableDefault(size = 10, sort = {"Name"}) Pageable pageable){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(importanceService.FindAll(id, pageable), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity Register(@RequestHeader("Authorization") String authHeader,
                                   @RequestParam(required = false, defaultValue = "0") Integer userId,
                                   @RequestBody RegisterImportanceDTO register){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(importanceService.Create(register, id), HttpStatus.OK);
    }

    @DeleteMapping("/{importanceId}")
    public ResponseEntity Delete(@PathVariable int importanceId,
                                 @RequestHeader("Authorization") String authHeader,
                                 @RequestParam(required = false, defaultValue = "0") Integer userId){
        int id = (userId != 0) ? checkVisibilite.CheckAccess(authHeader, userId) : checkVisibilite.ExtractIdFromToken(authHeader);
        importanceService.RemoveMedicine(importanceId, id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
