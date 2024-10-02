package bkd.src.salus.api.Repository.Treatment;

import bkd.src.salus.api.Domain.Entity.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.Treatment.Treatment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITreatmentRepositoryJPA extends JpaRepository<Treatment, Integer> {

    @Query("""
            SELECT t
            FROM Treatment t
            WHERE t.Name = :name
            AND t.User.Id = :userId
            AND t.Finished = false
            """)
    Treatment findTreatmentByName(String name, int userId);

    @Query("""
            SELECT t
            FROM Treatment t
            WHERE t.User.Id = :id
            """)
    Page<Treatment> findTreatmentByUserIdPageble(@Param("id") int id, Pageable pageable);

    @Query("""
            SELECT t
            FROM Treatment t
            WHERE t.Id = :id
            AND t.User.Id = :userId
            """)
    Treatment findTreatmentByUserIdAndId(int id, int userId);

}
