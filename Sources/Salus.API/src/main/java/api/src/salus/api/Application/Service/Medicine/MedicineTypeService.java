package api.src.salus.api.Application.Service.Medicine;

import api.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import api.src.salus.api.Domain.Entity.Medicine.MedicineType;
import api.src.salus.api.Domain.Interface.Application.Medicine.IMedicineTypeService;
import api.src.salus.api.Repository.Medicine.IMedicineTypeRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineTypeService implements IMedicineTypeService {

    private final IMedicineTypeRepositoryJPA typeRepository;

    @Autowired
    public MedicineTypeService(IMedicineTypeRepositoryJPA typeRepository){
        this.typeRepository = typeRepository;
    }

    @Override
    public List<MedicineType> ListAll(){
        return typeRepository.findAll();
    }
}
