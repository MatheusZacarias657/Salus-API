package api.src.salus.api.Application.Service.Importance;

import api.src.salus.api.Domain.DTO.Importance.DetailingImportanceDTO;
import api.src.salus.api.Domain.DTO.Importance.RegisterImportanceDTO;
import api.src.salus.api.Domain.Entity.Cataloging.Importance;
import api.src.salus.api.Domain.Entity.User.UserAccount;
import api.src.salus.api.Domain.Exception.ValidationException;
import api.src.salus.api.Domain.Interface.Application.IImportanceService;
import api.src.salus.api.Repository.Cataloging.IImportanceRepositoryJPA;
import api.src.salus.api.Repository.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ImportanceService implements IImportanceService {

    private final IImportanceRepositoryJPA importanceRepository;
    private final IUserRepositoryJPA userRepository;

    @Autowired
    public ImportanceService(IImportanceRepositoryJPA importanceRepository, IUserRepositoryJPA userRepository){
        this.importanceRepository = importanceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DetailingImportanceDTO Create(RegisterImportanceDTO register, int userId){
        if (importanceRepository.findImportanceByName(register.getName(), userId) != null){
            throw new ValidationException( "This importance already exists");
        }

        UserAccount user = userRepository.getReferenceById(userId);
        Importance entity = new Importance(register, user);
        importanceRepository.save(entity);

        return new DetailingImportanceDTO(entity);
    }

    @Override
    public Page<DetailingImportanceDTO> FindAll(int userId, Pageable pageable){
        return importanceRepository.importanceRepository(userId, pageable).map(DetailingImportanceDTO::new);
    }

    @Override
    public void RemoveMedicine(int importanceId, int userId){
        Importance entity = importanceRepository.findImportanceById(importanceId, userId);
        importanceRepository.delete(entity);
    }
}
