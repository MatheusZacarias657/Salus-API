package api.src.salus.api.Application.Service.Patient;

import api.src.salus.api.Domain.DTO.Patient.Allergy.DetailingPatientAllergyDTO;
import api.src.salus.api.Domain.DTO.Patient.Allergy.RegisterPatientAllergyDTO;
import api.src.salus.api.Domain.DTO.Patient.Disease.DetailingPatientDiseaseDTO;
import api.src.salus.api.Domain.DTO.Patient.Disease.RegisterPatientDiseaseDTO;
import api.src.salus.api.Domain.Entity.Patient.Patient;
import api.src.salus.api.Domain.Entity.Patient.PatientAllergy;
import api.src.salus.api.Domain.Entity.Patient.PatientDisease;
import api.src.salus.api.Domain.Interface.Application.Patient.IPatientComponentService;
import api.src.salus.api.Repository.Patient.IPatientAllergyRepositoryJPA;
import api.src.salus.api.Repository.Patient.IPatientDiseaseRepositoryJPA;
import api.src.salus.api.Repository.Patient.IPatientRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientAllergyService implements IPatientComponentService<DetailingPatientAllergyDTO, RegisterPatientAllergyDTO> {
    private final IPatientAllergyRepositoryJPA allergyRepository;
    private final IPatientRepositoryJPA patientRepository;

    @Autowired
    public PatientAllergyService(IPatientAllergyRepositoryJPA diseaseRepository, IPatientRepositoryJPA patientRepository) {
        this.allergyRepository = diseaseRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<DetailingPatientAllergyDTO> AddPatienComponent(List<RegisterPatientAllergyDTO> registers, int userId) {
        Patient patientEntity = patientRepository.findPatientByUserId(userId);
        List<PatientAllergy> entities = new ArrayList<>();

        for(RegisterPatientAllergyDTO allergy : registers){
            entities.add(new PatientAllergy(allergy, patientEntity));
        }

        entities = allergyRepository.saveAll(entities);

        return (List<DetailingPatientAllergyDTO>) entities.stream().map(DetailingPatientAllergyDTO::new);
    }

    @Override
    public List<DetailingPatientAllergyDTO> ListComponents(int userId) {
        return (List<DetailingPatientAllergyDTO>) allergyRepository.findPatientAllergiesByUserId(userId).stream().map(DetailingPatientAllergyDTO::new);
    }

    @Override
    public void DeleteComponent(int componentId, int userId) {
        PatientAllergy allergy = allergyRepository.findPatientAllergiesByUserIdAndAllergyId(componentId, userId);
        allergyRepository.delete(allergy);
    }
}
