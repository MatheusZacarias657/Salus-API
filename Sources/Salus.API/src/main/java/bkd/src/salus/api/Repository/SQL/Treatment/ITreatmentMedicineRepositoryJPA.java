package bkd.src.salus.api.Repository.SQL.Treatment;

import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
            WHERE t.Treatment.User.Id = :userId
            AND :date BETWEEN t.TreatmentInit AND t.TreatmentEnd
            """)
    Optional<List<TreatmentMedicine>> findMedicineTreatmentsByDate(int userId, LocalDateTime date);
}
