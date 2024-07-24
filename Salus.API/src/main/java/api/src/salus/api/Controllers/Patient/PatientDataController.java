package api.src.salus.api.Controllers.Patient;

import api.src.salus.api.Domain.DTO.Patient.Data.PatientModifierResponseDTO;
import api.src.salus.api.Domain.DTO.Patient.Data.RegisterPatientDataDTO;
import api.src.salus.api.Domain.DTO.Patient.Data.UpdatePatientDataDTO;
import api.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import api.src.salus.api.Domain.Interface.Application.Patient.IPatientCrudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Patient")
public class PatientDataController {

    private final IPatientCrudService<PatientModifierResponseDTO, RegisterPatientDataDTO, UpdatePatientDataDTO> patientDataService;
    private final ICheckVisibilite checkVisibilite;

    public PatientDataController(IPatientCrudService<PatientModifierResponseDTO, RegisterPatientDataDTO, UpdatePatientDataDTO> patientDataService, ICheckVisibilite checkVisibilite) {
        this.patientDataService = patientDataService;
        this.checkVisibilite = checkVisibilite;
    }

    @GetMapping
    public ResponseEntity GetPatientData(@RequestHeader("Authorization") String authHeader) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(patientDataService.GetPatientContent(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity CreatePatientData(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid RegisterPatientDataDTO patientData) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(patientDataService.RegisterPatientContent(patientData, userId), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity UpdatePatientData(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid UpdatePatientDataDTO patientData) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(patientDataService.UpdatePatientContent(patientData, userId), HttpStatus.OK);
    }
}
