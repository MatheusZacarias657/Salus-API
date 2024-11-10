package bkd.src.salus.api.Repository.SQL.Medicine;

import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            """)
    List<Medicine> findMedicineByUserId(@Param("id") int id);

    @Query("""
            SELECT m
            FROM Medicine m
            WHERE m.User.Id = :userId
            AND m.Removed = false
            AND m.Id = :id
            """)
    Medicine findMedicineByUserIdAndId(int id, int userId);

    @Query("""
            SELECT COUNT (m)
            FROM Medicine m
            WHERE m.HardwareId = :hardwareId
            """)
    int findDrawerUse(String hardwareId);
}
