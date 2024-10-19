package bkd.src.salus.api.Repository.SQL.Cataloging;

import bkd.src.salus.api.Domain.Entity.SQL.Cataloging.Importance;
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
    List<Importance> importanceRepository(int userId);
}
