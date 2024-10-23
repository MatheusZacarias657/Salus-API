package bkd.src.salus.api.Application.Service.Patient;

import bkd.src.salus.api.Domain.DTO.Patient.Allergy.DetailingPatientAllergyDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Allergy.RegisterPatientAllergyDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.PatientAllergy;
import bkd.src.salus.api.Domain.Interface.Application.Patient.IPatientComponentService;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientAllergyRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
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

        for(RegisterPatientAllergyDTO registerAllergy : registers){
            PatientAllergy allergy = allergyRepository.findPatientAllergiesByUserIdAndName(userId, registerAllergy.getAllergy());

            if(allergy == null){
                entities.add(new PatientAllergy(registerAllergy, patientEntity));
            }
        }

        if(!entities.isEmpty()){
            entities = allergyRepository.saveAll(entities);
        }

        return entities.stream().map(DetailingPatientAllergyDTO::new).toList();
    }

    @Override
    public List<DetailingPatientAllergyDTO> ListComponents(int userId) {
        return allergyRepository.findPatientAllergiesByUserId(userId).stream().map(DetailingPatientAllergyDTO::new).toList();
    }

    @Override
    public void DeleteComponent(int componentId, int userId) {
        PatientAllergy allergy = allergyRepository.findPatientAllergiesByUserIdAndAllergyId(componentId, userId);
        allergyRepository.delete(allergy);
    }
}
