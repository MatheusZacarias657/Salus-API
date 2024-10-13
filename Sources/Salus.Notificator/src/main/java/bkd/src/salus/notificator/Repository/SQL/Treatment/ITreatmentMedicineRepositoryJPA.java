package bkd.src.salus.notificator.Repository.SQL.Treatment;

import bkd.src.salus.notificator.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITreatmentMedicineRepositoryJPA extends JpaRepository<TreatmentMedicine, Integer> {

    @Query("""
            SELECT t
            FROM TreatmentMedicine t
            WHERE t.Treatment.Id = :treatmentId
            """)
    List<TreatmentMedicine> findMedicineTreatmentsByTreamentId(int treatmentId);

    @Query("""
            SELECT t
            FROM TreatmentMedicine t
            WHERE t.Medicine.Id = :medicineId
            """)
    TreatmentMedicine findMedicineTreatmentsByMedicineId(int medicineId);
}
