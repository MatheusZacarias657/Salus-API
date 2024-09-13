package api.src.salus.api.Repository.Treatment;

import api.src.salus.api.Domain.Entity.Treatment.Treatment;
import api.src.salus.api.Domain.Entity.Treatment.TreatmentMedicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITreatmentMedicineRepositoryJPA extends JpaRepository<TreatmentMedicine, Integer> {

    @Query("""
            SELECT t
            FROM TreatmentMedicine t
            WHERE t.Treatment.Id = :treatmentId
            """)
    Page<TreatmentMedicine> findMedicineTreatmentsByTreamentId(int treatmentId, Pageable pageable);
}
