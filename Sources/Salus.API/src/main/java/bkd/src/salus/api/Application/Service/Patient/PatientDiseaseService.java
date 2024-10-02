package bkd.src.salus.api.Application.Service.Patient;

import bkd.src.salus.api.Domain.DTO.Patient.Disease.DetailingPatientDiseaseDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Disease.RegisterPatientDiseaseDTO;
import bkd.src.salus.api.Domain.Entity.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.Patient.PatientDisease;
import bkd.src.salus.api.Domain.Interface.Application.Patient.IPatientComponentService;
import bkd.src.salus.api.Repository.Patient.IPatientDiseaseRepositoryJPA;
import bkd.src.salus.api.Repository.Patient.IPatientRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        for(RegisterPatientDiseaseDTO disease : registers){
            entities.add(new PatientDisease(disease, patientEntity));
        }

        entities = diseaseRepository.saveAll(entities);

        return (List<DetailingPatientDiseaseDTO>) entities.stream().map(DetailingPatientDiseaseDTO::new);
    }

    @Override
    public List<DetailingPatientDiseaseDTO> ListComponents(int userId) {
        return (List<DetailingPatientDiseaseDTO>) diseaseRepository.findPatientDiseasesByUserId(userId).stream().map(DetailingPatientDiseaseDTO::new);
    }

    @Override
    public void DeleteComponent(int componentId, int userId) {
        PatientDisease disease = diseaseRepository.findPatientDiseasesByUserIdAndDiseaseId(componentId, userId);
        diseaseRepository.delete(disease);
    }
}
