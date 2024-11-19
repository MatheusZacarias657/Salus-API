package bkd.src.salus.api.Application.Service.Patient;

import bkd.src.salus.api.Domain.DTO.Patient.Data.PatientModifierResponseDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Data.RegisterPatientDataDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Data.UpdatePatientDataDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Exception.ValidationException;
import bkd.src.salus.api.Domain.Interface.Application.Patient.IPatientCrudService;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientDataService implements IPatientCrudService<PatientModifierResponseDTO, RegisterPatientDataDTO, UpdatePatientDataDTO> {

    private final IPatientRepositoryJPA patientRepository;
    private final IUserRepositoryJPA userRepositoryJPA;

    @Autowired
    public PatientDataService(IPatientRepositoryJPA patientRepository, IUserRepositoryJPA userRepositoryJPA) {
        this.patientRepository = patientRepository;
        this.userRepositoryJPA = userRepositoryJPA;
    }

    @Override
    public PatientModifierResponseDTO RegisterPatientContent(RegisterPatientDataDTO patientRegister, int userId) {
        Patient testPatient = patientRepository.findPatientByUserId(userId);

        if(testPatient != null){
            throw new ValidationException("Patient Already exist");
        }

        UserAccount userEntity = userRepositoryJPA.getReferenceById(userId);
        Patient entity = new Patient(patientRegister, userEntity);
        entity = patientRepository.save(entity);

        return new PatientModifierResponseDTO(entity);
    }

    @Override
    public PatientModifierResponseDTO UpdatePatientContent(UpdatePatientDataDTO patientUpdate, int userId) {
        Patient entity = patientRepository.findPatientByUserId(userId);
        entity.Update(patientUpdate);
        entity = patientRepository.save(entity);

        return new PatientModifierResponseDTO(entity);
    }

    @Override
    public PatientModifierResponseDTO GetPatientContent(int userId) {
        Patient entity = patientRepository.findPatientByUserId(userId);

        return (entity != null) ? new PatientModifierResponseDTO(entity) : null;
    }
}
