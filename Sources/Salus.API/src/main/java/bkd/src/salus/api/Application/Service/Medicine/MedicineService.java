package bkd.src.salus.api.Application.Service.Medicine;

import bkd.src.salus.api.Domain.DTO.File.FileDetailingDTO;
import bkd.src.salus.api.Domain.DTO.Medicine.DetailingMedicineDTO;
import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Cataloging.Importance;
import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.SQL.Medicine.MedicineType;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Exception.ValidationException;
import bkd.src.salus.api.Domain.Interface.Application.FileManager.IMedicinePictureService;
import bkd.src.salus.api.Domain.Interface.Application.Logger.ILogMedicine;
import bkd.src.salus.api.Domain.Interface.Application.Medicine.*;
import bkd.src.salus.api.Repository.SQL.Cataloging.IImportanceRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Medicine.IMedicineRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Medicine.IMedicineTypeRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicineService implements IMedicineService, IMedicineOperator {

    private final IMedicineRepositoryJPA repository;
    private final IMedicineTypeRepositoryJPA typeRepository;
    private final IImportanceRepositoryJPA importanceRepository;
    private final IUserRepositoryJPA userRepository;
    private final ILogMedicine logMedicine;
    private final IMedicinePictureService pictureService;

    @Autowired
    public MedicineService(IMedicineRepositoryJPA repository, IMedicineTypeRepositoryJPA typeRepository, IImportanceRepositoryJPA importanceRepository, IUserRepositoryJPA userRepository, ILogMedicine logMedicine, IMedicinePictureService pictureService){
        this.repository = repository;
        this.typeRepository = typeRepository;
        this.importanceRepository = importanceRepository;
        this.userRepository = userRepository;
        this.logMedicine = logMedicine;
        this.pictureService = pictureService;
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

        Importance importance = (register.getImportance().matches("\\d+"))
                ? importanceRepository.findImportanceById(Integer.parseInt(register.getImportance()), userId)
                : importanceRepository.findImportanceByName(register.getImportance(), userId);

        Medicine entity = new Medicine(register, user, type, importance);
        repository.save(entity);

        return new DetailingMedicineDTO(entity);
    }

    @Override
    public DetailingMedicineDTO Find(int medicineId, int userId){
        Medicine entity = repository.findMedicineByUserIdAndId(medicineId, userId);
        List<String> urls = pictureService.FindByMedicineId(medicineId, userId).stream().map(FileDetailingDTO::getAvailableOn).toList();
        return new DetailingMedicineDTO(entity, urls);
    }

    @Override
    public List<DetailingMedicineDTO> FindAll(int userId){
        List<DetailingMedicineDTO> responses = repository.findMedicineByUserId(userId).stream().map(DetailingMedicineDTO::new).toList();

        for(DetailingMedicineDTO response : responses){
            List<String> urls = pictureService.FindByMedicineId(response.getId(), userId).stream().map(FileDetailingDTO::getAvailableOn).toList();
            response.AddLinks(urls);
        }

        return responses;
    }

    @Override
    public Map<Integer, String> FindNames(int userId){
        Map<Integer, String> names = new HashMap<>();

        for(Medicine entity : repository.findMedicineByUserId(userId)){
            names.put(entity.getId(), entity.getName());
        }

        return names;
    }

    @Override
    public void RemoveMedicine(int medicineId, int userId){
        Medicine entity = repository.findMedicineByUserIdAndId(medicineId, userId);
        entity.Remove();
        repository.save(entity);
    }

    @Override
    public void DecrementMedicine(int medicineId, int userId, int quantity){
        Medicine entity = repository.findMedicineByUserIdAndId(medicineId, userId);
        entity.Decrement(quantity);
        repository.save(entity);
        logMedicine.LogConsume(userId, medicineId);
    }
}
