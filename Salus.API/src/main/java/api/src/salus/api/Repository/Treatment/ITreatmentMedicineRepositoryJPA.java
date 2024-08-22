package api.src.salus.api.Repository.Treatment;

import api.src.salus.api.Domain.Entity.Treatment.Treatment;
import api.src.salus.api.Domain.Entity.Treatment.TreatmentMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITreatmentMedicineRepositoryJPA extends JpaRepository<TreatmentMedicine, Integer> {

}
