package api.src.salus.api.Application.Service.Medicine;

import api.src.salus.api.Domain.DTO.Medicine.DetailingMedicineDTO;
import api.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import api.src.salus.api.Domain.DTO.Medicine.UpdateMedicineDTO;
import api.src.salus.api.Domain.DTO.Patient.Allergy.DetailingPatientAllergyDTO;
import api.src.salus.api.Domain.Entity.Cataloging.Importance;
import api.src.salus.api.Domain.Entity.Medicine.Medicine;
import api.src.salus.api.Domain.Entity.Medicine.MedicineType;
import api.src.salus.api.Domain.Entity.Medicine.MedicineUnitType;
import api.src.salus.api.Domain.Entity.User.UserAccount;
import api.src.salus.api.Domain.Exception.ArgumentException;
import api.src.salus.api.Domain.Exception.ValidationException;
import api.src.salus.api.Domain.Interface.Application.Medicine.IMedicineService;
import api.src.salus.api.Repository.Cataloging.IImportanceRepositoryJPA;
import api.src.salus.api.Repository.Medicine.IMedicineRepositoryJPA;
import api.src.salus.api.Repository.Medicine.IMedicineTypeRepositoryJPA;
import api.src.salus.api.Repository.Medicine.IMedicineUnitTypeRepositoryJPA;
import api.src.salus.api.Repository.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineService implements IMedicineService {

    private final IMedicineRepositoryJPA repository;
    private final IMedicineTypeRepositoryJPA typeRepository;
    private final IMedicineUnitTypeRepositoryJPA unitTypeRepository;
    private final IImportanceRepositoryJPA importanceRepository;
    private final IUserRepositoryJPA userRepository;

    @Autowired
    public MedicineService(IMedicineRepositoryJPA repository, IMedicineTypeRepositoryJPA typeRepository, IMedicineUnitTypeRepositoryJPA unitTypeRepository, IImportanceRepositoryJPA importanceRepository, IUserRepositoryJPA userRepository){
        this.repository = repository;
        this.typeRepository = typeRepository;
        this.unitTypeRepository = unitTypeRepository;
        this.importanceRepository = importanceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DetailingMedicineDTO Create(RegisterMedicineDTO register, int userId){
        if (repository.findMedicineByName(register.getName(), userId) != null){
            throw new ValidationException( "This medicine already exists");
        }

        UserAccount user = userRepository.getReferenceById(userId);
        MedicineType type = (register.getType().matches("\\d+"))
                ? typeRepository.getReferenceById(Integer.parseInt(register.getType()))
                : typeRepository.findTypeByName(register.getType());

        MedicineUnitType unitType = (register.getType().matches("\\d+"))
                ? unitTypeRepository.getReferenceById(Integer.parseInt(register.getUnitType()))
                : unitTypeRepository.findUnitTypeByName(register.getUnitType());

        Importance importance = (register.getType().matches("\\d+"))
                ? importanceRepository.getReferenceById(Integer.parseInt(register.getImportance()))
                : importanceRepository.findImportanceByName(register.getImportance());

        Medicine entity = new Medicine(register, user, type, unitType, importance);
        repository.save(entity);

        return new DetailingMedicineDTO(entity);
    }

    @Override
    public DetailingMedicineDTO Find(int medicineId, int userId){
        Medicine entity = repository.findMedicineByUserIdAndId(medicineId, userId);
        return new DetailingMedicineDTO(entity);
    }

    @Override
    public List<DetailingMedicineDTO> FindAll(int userId){
        List<Medicine> entities = repository.findMedicineByUserId(userId);
        return (List<DetailingMedicineDTO>) entities.stream().map(DetailingMedicineDTO::new);
    }

    @Override
    public List<String> FindNames(int userId){
        List<String> names = new ArrayList<String>();

        for(Medicine entity : repository.findMedicineByUserId(userId)){
            names.add(entity.getName());
        }

        return  names;
    }

    @Override
    public void RemoveMedicine(int medicineId, int userId){
        Medicine entity = repository.findMedicineByUserIdAndId(medicineId, userId);
        entity.Remove();
        repository.save(entity);
    }

    @Override
    public DetailingMedicineDTO Update(int medicineId, int userId, UpdateMedicineDTO update) {
        Medicine entity = repository.findMedicineByUserIdAndId(medicineId, userId);
        entity.Update(update);
        repository.save(entity);

        return new DetailingMedicineDTO(entity);
    }
}
