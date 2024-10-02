package bkd.src.salus.api.Controllers.Patient;

import bkd.src.salus.api.Domain.DTO.Patient.Address.PatientAddressModifierResponseDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Address.RegisterPatientAddressDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Address.UpdatePatientAddressDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Data.RegisterPatientDataDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Data.UpdatePatientDataDTO;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.Patient.IPatientCrudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Patient/Address")
public class PatientAddressController {

    private final IPatientCrudService<PatientAddressModifierResponseDTO, RegisterPatientAddressDTO, UpdatePatientAddressDTO> patientAddressService;
    private final ICheckVisibilite checkVisibilite;

    public PatientAddressController(IPatientCrudService<PatientAddressModifierResponseDTO, RegisterPatientAddressDTO, UpdatePatientAddressDTO> patientAddressService, ICheckVisibilite checkVisibilite) {
        this.patientAddressService = patientAddressService;
        this.checkVisibilite = checkVisibilite;
    }

    @GetMapping
    public ResponseEntity GetPatientData(@RequestHeader("Authorization") String authHeader) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(patientAddressService.GetPatientContent(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity CreatePatientData(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid RegisterPatientAddressDTO patientAddressDTO) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(patientAddressService.RegisterPatientContent(patientAddressDTO, userId), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity UpdatePatientData(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid UpdatePatientAddressDTO patientAddressDTO) throws Exception{
        int userId = checkVisibilite.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(patientAddressService.UpdatePatientContent(patientAddressDTO, userId), HttpStatus.OK);
    }
}
