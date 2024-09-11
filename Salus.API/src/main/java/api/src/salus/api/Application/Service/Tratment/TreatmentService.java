package api.src.salus.api.Application.Service.Tratment;

import api.src.salus.api.Domain.DTO.Treatment.DetailingTreatmentDTO;
import api.src.salus.api.Domain.DTO.Treatment.RegisterTreatmentDTO;
import api.src.salus.api.Domain.Entity.Cataloging.Importance;
import api.src.salus.api.Domain.Entity.Treatment.Treatment;
import api.src.salus.api.Domain.Entity.User.UserAccount;
import api.src.salus.api.Domain.Exception.ValidationException;
import api.src.salus.api.Domain.Interface.Application.Treatment.ITreatmentService;
import api.src.salus.api.Repository.Cataloging.IImportanceRepositoryJPA;
import api.src.salus.api.Repository.Treatment.ITreatmentRepositoryJPA;
import api.src.salus.api.Repository.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

@Service
public class TreatmentService implements ITreatmentService {

    private final ITreatmentRepositoryJPA repository;
    private final IUserRepositoryJPA userRepository;
    private final IImportanceRepositoryJPA importanceRepository;

    @Autowired
    public TreatmentService(ITreatmentRepositoryJPA repository, IUserRepositoryJPA userRepository, IImportanceRepositoryJPA importanceRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.importanceRepository = importanceRepository;
    }

    @Override
    public DetailingTreatmentDTO Create(RegisterTreatmentDTO register, int userId){

        if (repository.findTreatmentByName(register.getName(), userId) != null){
            throw new ValidationException( "This treatment already exists");
        }

        UserAccount user = userRepository.getReferenceById(userId);

        Importance importance = (register.getImportance().matches("\\d+"))
                ? importanceRepository.getReferenceById(Integer.parseInt(register.getImportance()))
                : importanceRepository.findImportanceByName(register.getImportance(), userId);

        Treatment entity = new Treatment(register.getName(), user, importance);
        repository.save(entity);

        return new DetailingTreatmentDTO(entity);
    }

    @Override
    public Page<DetailingTreatmentDTO> FindAll(int userId, Pageable pageable){
        return repository.findTreatmentByUserIdPageble(userId, pageable).map(DetailingTreatmentDTO::new);
    }

    @Override
    public DetailingTreatmentDTO Find(int treatmentId, int userId){
        Treatment entity = repository.findTreatmentByUserIdAndId(treatmentId, userId);
        //TODO: retornar os medicamentos junto
        return new DetailingTreatmentDTO(entity);
    }

    @Override
    public void Delete(int treatmentId, int userId){
        Treatment entity = repository.findTreatmentByUserIdAndId(treatmentId, userId);
        entity.Finish();
        repository.save(entity);
    }
}