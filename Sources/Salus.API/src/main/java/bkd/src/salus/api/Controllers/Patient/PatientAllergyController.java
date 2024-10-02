package bkd.src.salus.api.Controllers.Patient;

import bkd.src.salus.api.Domain.DTO.Patient.Allergy.DetailingPatientAllergyDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Allergy.RegisterPatientAllergyDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Disease.DetailingPatientDiseaseDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Disease.RegisterPatientDiseaseDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.Patient.IPatientComponentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Patient/Allergy")
public class PatientAllergyController {

    private final ICheckVisibilite checkVisibilite;
    private final IPatientComponentService<DetailingPatientAllergyDTO, RegisterPatientAllergyDTO> allergyService;

    public PatientAllergyController(ICheckVisibilite checkVisibilite, IPatientComponentService<DetailingPatientAllergyDTO, RegisterPatientAllergyDTO> allergyService) {
        this.checkVisibilite = checkVisibilite;
        this.allergyService = allergyService;
    }

    @GetMapping("AllAlergies")
    public ResponseEntity ListAllDeseases(@RequestHeader("Authorization") String authHeader) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(allergyService.ListComponents(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity CreateDesease(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid List<RegisterPatientAllergyDTO> registers) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(allergyService.AddPatienComponent(registers, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{diseaseId}")
    public ResponseEntity RemoveDesease(@RequestHeader("Authorization") String authHeader, @PathVariable int diseaseId) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        allergyService.DeleteComponent(diseaseId, userId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
