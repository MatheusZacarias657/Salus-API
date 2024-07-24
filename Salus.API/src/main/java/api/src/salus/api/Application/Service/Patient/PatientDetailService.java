package api.src.salus.api.Application.Service.Patient;

import api.src.salus.api.Domain.DTO.Patient.Detail.PatientDetailModifierResponseDTO;
import api.src.salus.api.Domain.DTO.Patient.Detail.RegisterPatientDetailDTO;
import api.src.salus.api.Domain.DTO.Patient.Detail.UpdatePatientDetailDTO;
import api.src.salus.api.Domain.Entity.Patient.Patient;
import api.src.salus.api.Domain.Entity.Patient.PatientDetail;
import api.src.salus.api.Repository.Patient.IPatientDetailRepositoryJPA;
import api.src.salus.api.Repository.Patient.IPatientRepositoryJPA;
import api.src.salus.api.Domain.Interface.Application.Patient.IPatientCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientDetailService implements IPatientCrudService<PatientDetailModifierResponseDTO, RegisterPatientDetailDTO, UpdatePatientDetailDTO> {

    private final IPatientRepositoryJPA patientRepository;
    private final IPatientDetailRepositoryJPA patientDetailRepository;

    @Autowired
    public PatientDetailService(IPatientRepositoryJPA patientRepository, IPatientDetailRepositoryJPA patientDetailRepository) {
        this.patientRepository = patientRepository;
        this.patientDetailRepository = patientDetailRepository;
    }

    @Override
    public PatientDetailModifierResponseDTO RegisterPatientContent(RegisterPatientDetailDTO patientRegister, int userId) {
        Patient patientEntity = patientRepository.findPatientByUserId(userId);
        PatientDetail entity = new PatientDetail(patientRegister, patientEntity);
        entity = patientDetailRepository.save(entity);

        return new PatientDetailModifierResponseDTO(entity);
    }

    @Override
    public PatientDetailModifierResponseDTO UpdatePatientContent(UpdatePatientDetailDTO patientUpdate, int userId) {
        PatientDetail entity = patientDetailRepository.findPatientDetailByUserId(userId);
        entity.Update(patientUpdate);
        entity = patientDetailRepository.save(entity);

        return new PatientDetailModifierResponseDTO(entity);
    }

    @Override
    public PatientDetailModifierResponseDTO GetPatientContent(int userId) {
        PatientDetail entity = patientDetailRepository.findPatientDetailByUserId(userId);

        return new PatientDetailModifierResponseDTO(entity);
    }
}
