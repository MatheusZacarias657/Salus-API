package bkd.src.salus.api.Repository.SQL.Medicine;

import bkd.src.salus.api.Domain.Entity.SQL.Medicine.MedicineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IMedicineTypeRepositoryJPA extends JpaRepository<MedicineType, Integer> {

    @Query("""
            SELECT m
            FROM MedicineType m
            WHERE m.Name = :name
            """)
    MedicineType findTypeByName(String name);
}
