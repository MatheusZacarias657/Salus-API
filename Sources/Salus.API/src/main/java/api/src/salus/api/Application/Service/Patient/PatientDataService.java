package api.src.salus.api.Application.Service.Patient;

import api.src.salus.api.Domain.DTO.Patient.Data.PatientModifierResponseDTO;
import api.src.salus.api.Domain.DTO.Patient.Data.RegisterPatientDataDTO;
import api.src.salus.api.Domain.DTO.Patient.Data.UpdatePatientDataDTO;
import api.src.salus.api.Domain.Entity.Patient.Patient;
import api.src.salus.api.Domain.Entity.User.UserAccount;
import api.src.salus.api.Domain.Interface.Application.Patient.IPatientCrudService;
import api.src.salus.api.Repository.Patient.IPatientRepositoryJPA;
import api.src.salus.api.Repository.User.IUserRepositoryJPA;
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

        return new PatientModifierResponseDTO(entity);
    }
}
