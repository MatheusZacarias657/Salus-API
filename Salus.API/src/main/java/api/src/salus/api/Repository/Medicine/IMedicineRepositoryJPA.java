package api.src.salus.api.Repository.Medicine;

import api.src.salus.api.Domain.Entity.Medicine.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMedicineRepositoryJPA extends JpaRepository<Medicine, Integer> {

    @Query("""
            SELECT m
            FROM Medicine m
            WHERE m.Name = :name
            AND m.User.Id = :userId
            AND m.Removed = false
            """)
    Medicine findMedicineByName(String name, int userId);

    @Query("""
            SELECT m
            FROM Medicine m
            WHERE m.User.Id = :id
            AND m.Removed = false
            """)
    List<Medicine> findMedicineByUserId(int id);

    @Query("""
            SELECT m
            FROM Medicine m
            WHERE m.User.Id = :userId
            AND m.Removed = false
            AND m.Id = :id
            """)
    Medicine findMedicineByUserIdAndId(int id, int userId);
}
