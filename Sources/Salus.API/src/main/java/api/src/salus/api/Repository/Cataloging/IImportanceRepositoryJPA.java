package api.src.salus.api.Repository.Cataloging;

import api.src.salus.api.Domain.Entity.Cataloging.Importance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IImportanceRepositoryJPA extends JpaRepository<Importance, Integer> {

    @Query("""
            SELECT i
            FROM Importance i
            WHERE i.Name = :name
            AND i.User.Id = :userId
            """)
    Importance findImportanceByName(String name, int userId);

    @Query("""
            SELECT i
            FROM Importance i
            WHERE i.Id = :importanceId
            AND i.User.Id = :userId
            """)
    Importance findImportanceById(int importanceId, int userId);

    @Query("""
            SELECT i
            FROM Importance i
            WHERE i.User.Id = :userId
            """)
    Page<Importance> importanceRepository(int userId, Pageable pageable);
}
