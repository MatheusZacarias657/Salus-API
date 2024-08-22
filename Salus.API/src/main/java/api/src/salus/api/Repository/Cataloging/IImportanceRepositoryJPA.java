package api.src.salus.api.Repository.Cataloging;

import api.src.salus.api.Domain.Entity.Cataloging.Importance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IImportanceRepositoryJPA extends JpaRepository<Importance, Integer> {

    @Query("""
            SELECT i
            FROM Importance i
            WHERE i.Name = :name
            """)
    Importance findImportanceByName(String name);
}
