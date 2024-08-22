package api.src.salus.api.Repository.Medicine;

import api.src.salus.api.Domain.Entity.Medicine.MedicineType;
import api.src.salus.api.Domain.Entity.Medicine.MedicineUnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IMedicineUnitTypeRepositoryJPA extends JpaRepository<MedicineUnitType, Integer> {

    @Query("""
            SELECT m
            FROM MedicineUnitType m
            WHERE m.Name = :name
            """)
    MedicineUnitType findUnitTypeByName(String name);
}
