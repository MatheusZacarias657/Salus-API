package bkd.src.salus.api.Repository.SQL.FAQ;

import bkd.src.salus.api.Domain.Entity.SQL.FAQ.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFAQRepositoryJPA extends JpaRepository<FAQ, Integer> {

    @Query("""
            SELECT f
            FROM FAQ f
            WHERE f.Group.Id = :id
            """)
    List<FAQ> findFAQByGroupId(int id);
}
