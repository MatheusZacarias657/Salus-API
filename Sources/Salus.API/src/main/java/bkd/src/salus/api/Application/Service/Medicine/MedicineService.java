package bkd.src.salus.api.Application.Service.Medicine;

import bkd.src.salus.api.Domain.DTO.Medicine.DetailingMedicineDTO;
import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Cataloging.Importance;
import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.SQL.Medicine.MedicineType;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Exception.ValidationException;
import bkd.src.salus.api.Domain.Interface.Application.Medicine.IMedicineService;
import bkd.src.salus.api.Repository.SQL.Cataloging.IImportanceRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Medicine.IMedicineRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Medicine.IMedicineTypeRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MedicineService implements IMedicineService {

    private final IMedicineRepositoryJPA repository;
    private final IMedicineTypeRepositoryJPA typeRepository;
    private final IImportanceRepositoryJPA importanceRepository;
    private final IUserRepositoryJPA userRepository;

    @Autowired
    public MedicineService(IMedicineRepositoryJPA repository, IMedicineTypeRepositoryJPA typeRepository, IImportanceRepositoryJPA importanceRepository, IUserRepositoryJPA userRepository){
        this.repository = repository;
        this.typeRepository = typeRepository;
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
        return new DetailingMedicineDTO(entity);
    }

    @Override
    public Page<DetailingMedicineDTO> FindAll(int userId, Pageable pageable){
        return repository.findMedicineByUserIdPageble(userId, pageable).map(DetailingMedicineDTO::new);
    }

    @Override
    public Map<Integer, String> FindNames(int userId){
        Map<Integer, String> names = new HashMap<Integer, String>();

        for(Medicine entity : repository.findMedicineByUserId(userId)){
            names.put(entity.getId(), entity.getName());
        }

        return  names;
    }

    @Override
    public void RemoveMedicine(int medicineId, int userId){
        Medicine entity = repository.findMedicineByUserIdAndId(medicineId, userId);
        entity.Remove();
        repository.save(entity);
    }
}
