package bkd.src.salus.notificator.Repository.SQL.Treatment;

import bkd.src.salus.notificator.Domain.Entity.Treatment.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ITreatmentRepositoryJPA extends JpaRepository<Treatment, Integer> {

    @Query("""
            SELECT t
            FROM Treatment t
            WHERE t.Id = :id
            """)
    Treatment findByTreatmentId(@Param("id") int id);
}
