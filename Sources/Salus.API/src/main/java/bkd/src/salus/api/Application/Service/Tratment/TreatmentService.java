package bkd.src.salus.api.Application.Service.Tratment;

import bkd.src.salus.api.Domain.DTO.Treatment.*;
import bkd.src.salus.api.Domain.Entity.SQL.Cataloging.Importance;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.Treatment;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Exception.ValidationException;
import bkd.src.salus.api.Domain.Interface.Application.Treatment.ITreatmentMedicineService;
import bkd.src.salus.api.Domain.Interface.Application.Treatment.ITreatmentService;
import bkd.src.salus.api.Repository.SQL.Cataloging.IImportanceRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Treatment.ITreatmentRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

@Service
public class TreatmentService implements ITreatmentService {

    private final ITreatmentRepositoryJPA repository;
    private final IUserRepositoryJPA userRepository;
    private final IImportanceRepositoryJPA importanceRepository;
    private final ITreatmentMedicineService treatmentMedicineService;

    @Autowired
    public TreatmentService(ITreatmentRepositoryJPA repository, IUserRepositoryJPA userRepository, IImportanceRepositoryJPA importanceRepository, ITreatmentMedicineService treatmentMedicineService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.importanceRepository = importanceRepository;
        this.treatmentMedicineService = treatmentMedicineService;
    }

    @Override
    public CompleteDetailingTreatment Create(CompleteRegisterTreatment register, int userId){

        if (repository.findTreatmentByName(register.getTreatment().getName(), userId) != null){
            throw new ValidationException( "This treatment already exists");
        }

        UserAccount user = userRepository.getReferenceById(userId);

        String importanceName = register.getTreatment().getImportance();
        Importance importance = (importanceName.matches("\\d+"))
                ? importanceRepository.getReferenceById(Integer.parseInt(importanceName))
                : importanceRepository.findImportanceByName(importanceName, userId);

        Treatment entity = new Treatment(register.getTreatment().getName(), user, importance);
        repository.save(entity);
        List<DetailingTreatmentMedicineDTO> medicines = treatmentMedicineService.Register(register.getMedicines(), entity);

        return new CompleteDetailingTreatment(new DetailingTreatmentDTO(entity), medicines);
    }

    @Override
    public Page<DetailingTreatmentDTO> FindAll(int userId, Pageable pageable){
        return repository.findTreatmentByUserIdPageble(userId, pageable).map(DetailingTreatmentDTO::new);
    }

    @Override
    public CompleteDetailingTreatment Find(int treatmentId, int userId, Pageable pageable){
        Treatment entity = repository.findTreatmentByUserIdAndId(treatmentId, userId);
        Page<DetailingTreatmentMedicineDTO> medicines = treatmentMedicineService.FindByTreatmentId(entity.getId(), pageable);

        return new CompleteDetailingTreatment(new DetailingTreatmentDTO(entity), medicines.getContent());
    }

    @Override
    public void Delete(int treatmentId, int userId){
        Treatment entity = repository.findTreatmentByUserIdAndId(treatmentId, userId);
        entity.Finish();
        repository.save(entity);
    }
}