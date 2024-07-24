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
@RequestMapping("/Patient/Detail")
public class PatientDetailController {

    private final IPatientCrudService<PatientDetailModifierResponseDTO, RegisterPatientDetailDTO, UpdatePatientDetailDTO> patientDataService;
    private final ICheckVisibilite checkVisibilite;

    public PatientDetailController(IPatientCrudService<PatientDetailModifierResponseDTO, RegisterPatientDetailDTO, UpdatePatientDetailDTO> patientDataService, ICheckVisibilite checkVisibilite) {
        this.patientDataService = patientDataService;
        this.checkVisibilite = checkVisibilite;
    }

    @GetMapping
    public ResponseEntity GetPatientData(@RequestHeader("Authorization") String authHeader) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(patientDataService.GetPatientContent(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity CreatePatientData(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid RegisterPatientDetailDTO patientData) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(patientDataService.RegisterPatientContent(patientData, userId), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity UpdatePatientData(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid UpdatePatientDetailDTO patientData) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(patientDataService.UpdatePatientContent(patientData, userId), HttpStatus.OK);
    }
}
