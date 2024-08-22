package api.src.salus.api.Repository.FAQ;

import api.src.salus.api.Domain.Entity.FAQ.FAQ;
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
