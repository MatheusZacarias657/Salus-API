package bkd.src.salus.api.Application.Service.Patient;

import bkd.src.salus.api.Domain.DTO.Patient.Allergy.RegisterPatientAllergyDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Disease.DetailingPatientDiseaseDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Disease.RegisterPatientDiseaseDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.PatientDisease;
import bkd.src.salus.api.Domain.Interface.Application.Patient.IPatientComponentService;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientDiseaseRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PatientDiseaseService implements IPatientComponentService<DetailingPatientDiseaseDTO,RegisterPatientDiseaseDTO> {
    private final IPatientDiseaseRepositoryJPA diseaseRepository;
    private final IPatientRepositoryJPA patientRepository;

    @Autowired
    public PatientDiseaseService(IPatientDiseaseRepositoryJPA diseaseRepository, IPatientRepositoryJPA patientRepository) {
        this.diseaseRepository = diseaseRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<DetailingPatientDiseaseDTO> AddPatienComponent(List<RegisterPatientDiseaseDTO> registers, int userId) {
        Patient patientEntity = patientRepository.findPatientByUserId(userId);
        List<PatientDisease> entities = new ArrayList<>();

        List<String> allDiseases = registers.stream().map(RegisterPatientDiseaseDTO::getDisease).toList();
        Set<String> uniqueNames = new HashSet<>(allDiseases);

        for(String registerDisease : uniqueNames){
            PatientDisease disease = diseaseRepository.findPatientDiseasesByUserIdAndName(userId, registerDisease);

            if(disease == null){
                entities.add(new PatientDisease(registerDisease, patientEntity));
            }
        }

        if(!entities.isEmpty()){
            entities = diseaseRepository.saveAll(entities);
        }

        return entities.stream().map(DetailingPatientDiseaseDTO::new).toList();
    }

    @Override
    public List<DetailingPatientDiseaseDTO> ListComponents(int userId) {
        return diseaseRepository.findPatientDiseasesByUserId(userId).stream().map(DetailingPatientDiseaseDTO::new).toList();
    }

    @Override
    public void DeleteComponent(int componentId, int userId) {
        PatientDisease disease = diseaseRepository.findPatientDiseasesByUserIdAndDiseaseId(userId, componentId);
        diseaseRepository.delete(disease);
    }
}
