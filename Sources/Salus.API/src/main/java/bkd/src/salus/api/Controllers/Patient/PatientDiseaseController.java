package bkd.src.salus.api.Controllers.Patient;

import bkd.src.salus.api.Domain.DTO.Patient.Detail.PatientDetailModifierResponseDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Detail.RegisterPatientDetailDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Detail.UpdatePatientDetailDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Disease.DetailingPatientDiseaseDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Disease.RegisterPatientDiseaseDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.Patient.IPatientComponentService;
import bkd.src.salus.api.Domain.Interface.Application.Patient.IPatientCrudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Patient/Disease")
public class PatientDiseaseController {

    private final ICheckVisibilite checkVisibilite;
    private final IPatientComponentService<DetailingPatientDiseaseDTO, RegisterPatientDiseaseDTO> diseaseService;

    public PatientDiseaseController(ICheckVisibilite checkVisibilite, IPatientComponentService<DetailingPatientDiseaseDTO, RegisterPatientDiseaseDTO> diseaseService) {
        this.checkVisibilite = checkVisibilite;
        this.diseaseService = diseaseService;
    }

    @GetMapping("AllDiseases")
    public ResponseEntity ListAllDeseases(@RequestHeader("Authorization") String authHeader) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(diseaseService.ListComponents(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity CreateDesease(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid List<RegisterPatientDiseaseDTO> registers) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(diseaseService.AddPatienComponent(registers, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{diseaseId}")
    public ResponseEntity RemoveDesease(@RequestHeader("Authorization") String authHeader, @PathVariable int diseaseId) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        diseaseService.DeleteComponent(diseaseId, userId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
