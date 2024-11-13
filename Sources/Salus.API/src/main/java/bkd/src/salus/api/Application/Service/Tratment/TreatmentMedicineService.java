package bkd.src.salus.api.Application.Service.Tratment;

import bkd.src.salus.api.Domain.DTO.File.FileDetailingDTO;
import bkd.src.salus.api.Domain.DTO.Medicine.MedicineCalendarDetailing;
import bkd.src.salus.api.Domain.DTO.Medicine.MedicineCalendarResponse;
import bkd.src.salus.api.Domain.DTO.Treatment.DetailingTreatmentMedicineDTO;
import bkd.src.salus.api.Domain.DTO.Treatment.RegisterMedicineTreatmentDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.Treatment;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.api.Domain.Interface.Application.FileManager.IMedicinePictureService;
import bkd.src.salus.api.Domain.Interface.Application.Logger.ILogTreatmentRegister;
import bkd.src.salus.api.Domain.Interface.Application.Treatment.ITreatmentMedicineService;
import bkd.src.salus.api.Repository.SQL.Medicine.IMedicineRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Treatment.ITreatmentMedicineRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TreatmentMedicineService implements ITreatmentMedicineService, IDayMedicineService {

    private final ITreatmentMedicineRepositoryJPA repository;
    private final IMedicineRepositoryJPA medicineRepository;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final IMedicinePictureService pictureService;
    private final ILogTreatmentRegister logTreatmentRegister;

    public TreatmentMedicineService(ITreatmentMedicineRepositoryJPA repository, IMedicineRepositoryJPA medicineRepository, IUserRepositoryJPA userRepositoryJPA, IMedicinePictureService pictureService, ILogTreatmentRegister logTreatmentRegister){
        this.repository = repository;
        this.medicineRepository = medicineRepository;
        this.userRepositoryJPA = userRepositoryJPA;
        this.pictureService = pictureService;
        this.logTreatmentRegister = logTreatmentRegister;
    }

    @Override
    @Transactional
    public List<DetailingTreatmentMedicineDTO> Register(List<RegisterMedicineTreatmentDTO> registers, Treatment treatment){
        List<DetailingTreatmentMedicineDTO> results = new ArrayList<>();
        List<TreatmentMedicine> entities = new ArrayList<>();

        for(RegisterMedicineTreatmentDTO register : registers){
            Medicine medicine = medicineRepository.findById(register.getMedicineId()).get();
            TreatmentMedicine entity = new TreatmentMedicine(register, medicine, treatment);
            entities.add(entity);
            results.add(new DetailingTreatmentMedicineDTO(entity));
        }

        repository.saveAll(entities);
        logTreatmentRegister.LogRegister(treatment.getUser(), treatment, entities);

        return results;
    }

    @Override
    public List<DetailingTreatmentMedicineDTO> FindByTreatmentId(int treatmentId){
        return repository.findMedicineTreatmentsByTreamentId(treatmentId).stream().map(DetailingTreatmentMedicineDTO::new).toList();
    }

    @Override
    public MedicineCalendarResponse FindByDay(int userId, LocalDateTime date){
        String user = userRepositoryJPA.findById(userId).get().getLogin();
        List<TreatmentMedicine> treatmentMedicines = repository.findMedicineTreatmentsByDate(userId, date);
        List<MedicineCalendarDetailing> responseMedicines = new ArrayList<>();

        for(TreatmentMedicine treatmentMedicine : treatmentMedicines){
            List<String> pictures = pictureService.FindByMedicineId(treatmentMedicine.getMedicine().getId(), userId).stream().map(FileDetailingDTO::getAvailableOn).toList();
            responseMedicines.add(new MedicineCalendarDetailing(treatmentMedicine, pictures));
        }

        return new MedicineCalendarResponse(user, responseMedicines);
    }
}
