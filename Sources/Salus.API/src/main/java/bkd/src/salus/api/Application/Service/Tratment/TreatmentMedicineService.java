package bkd.src.salus.api.Application.Service.Tratment;

import bkd.src.salus.api.Domain.DTO.Treatment.DetailingTreatmentMedicineDTO;
import bkd.src.salus.api.Domain.DTO.Treatment.RegisterMedicineTreatmentDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.Treatment;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.api.Domain.Interface.Application.Treatment.ITreatmentMedicineService;
import bkd.src.salus.api.Repository.SQL.Medicine.IMedicineRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Treatment.ITreatmentMedicineRepositoryJPA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreatmentMedicineService implements ITreatmentMedicineService {

    private final ITreatmentMedicineRepositoryJPA repository;
    private final IMedicineRepositoryJPA medicineRepository;

    public TreatmentMedicineService(ITreatmentMedicineRepositoryJPA repository, IMedicineRepositoryJPA medicineRepository){
        this.repository = repository;
        this.medicineRepository = medicineRepository;
    }

    @Override
    public List<DetailingTreatmentMedicineDTO> Register(List<RegisterMedicineTreatmentDTO> registers, Treatment treatment){
        List<DetailingTreatmentMedicineDTO> results = new ArrayList<>();

        for(RegisterMedicineTreatmentDTO register : registers){
            Medicine medicine = medicineRepository.getReferenceById(register.getMedicineId());
            TreatmentMedicine entity = new TreatmentMedicine(register, medicine, treatment);
            repository.save(entity);
            results.add(new DetailingTreatmentMedicineDTO(entity));
        }

        return results;
    }

    @Override
    public Page<DetailingTreatmentMedicineDTO> FindByTreatmentId(int treatmentId, Pageable pageable){
        return repository.findMedicineTreatmentsByTreamentId(treatmentId, pageable).map(DetailingTreatmentMedicineDTO::new);
    }
}
