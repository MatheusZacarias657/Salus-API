package api.src.salus.api.Application.Service.Patient;

import api.src.salus.api.Domain.DTO.Patient.Detail.PatientDetailModifierResponseDTO;
import api.src.salus.api.Domain.DTO.Patient.Detail.UpdatePatientDetailDTO;
import api.src.salus.api.Domain.DTO.Patient.Disease.RegisterPatientDiseaseDTO;
import api.src.salus.api.Domain.Entity.Patient.Patient;
import api.src.salus.api.Domain.Entity.Patient.PatientDetail;
import api.src.salus.api.Domain.Entity.Patient.PatientDisease;
import api.src.salus.api.Repository.Patient.IPatientDiseaseRepositoryJPA;
import api.src.salus.api.Repository.Patient.IPatientRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientDiseaseService {

    private final IPatientDiseaseRepositoryJPA diseaseRepository;
    private final IPatientRepositoryJPA patientRepository;
    @Autowired
    public PatientDiseaseService(IPatientDiseaseRepositoryJPA diseaseRepository, IPatientRepositoryJPA patientRepository) {
        this.diseaseRepository = diseaseRepository;
        this.patientRepository = patientRepository;
    }

    public void AddPatienComponent(List<RegisterPatientDiseaseDTO> registers, int userId) {
        Patient patientEntity = patientRepository.findPatientByUserId(userId);
        List<PatientDisease> entities = new ArrayList<>();

        for(RegisterPatientDiseaseDTO disease : registers){
            entities.add(new PatientDisease(disease, patientEntity));
        }

        diseaseRepository.saveAll(entities);
    }

    public PatientDetailModifierResponseDTO UpdatePatientContent(int diseaseId, int userId) {
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
