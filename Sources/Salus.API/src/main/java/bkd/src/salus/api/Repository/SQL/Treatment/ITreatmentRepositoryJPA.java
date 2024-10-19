package bkd.src.salus.api.Repository.SQL.Treatment;

import bkd.src.salus.api.Domain.Entity.SQL.Treatment.Treatment;
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
    List<Treatment> findTreatmentByUserId(@Param("id") int id);

    @Query("""
            SELECT t
            FROM Treatment t
            WHERE t.Id = :id
            AND t.User.Id = :userId
            """)
    Treatment findTreatmentByUserIdAndId(int id, int userId);

}
