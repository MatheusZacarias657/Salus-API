package api.src.salus.api.Controllers.Patient;

import api.src.salus.api.Domain.DTO.Patient.Detail.PatientDetailModifierResponseDTO;
import api.src.salus.api.Domain.DTO.Patient.Detail.RegisterPatientDetailDTO;
import api.src.salus.api.Domain.DTO.Patient.Detail.UpdatePatientDetailDTO;
import api.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import api.src.salus.api.Domain.Interface.Application.Patient.IPatientCrudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Patient/Disease")
public class PatientDiseaseController {

    private final ICheckVisibilite checkVisibilite;

    public PatientDiseaseController(ICheckVisibilite checkVisibilite) {
        this.checkVisibilite = checkVisibilite;
    }

    @GetMapping("AllDiseases")
    public ResponseEntity ListAllDeseases(@RequestHeader("Authorization") String authHeader) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity CreateDesease(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid RegisterPatientDetailDTO patientData) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity RemoveDesease(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid UpdatePatientDetailDTO patientData) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
