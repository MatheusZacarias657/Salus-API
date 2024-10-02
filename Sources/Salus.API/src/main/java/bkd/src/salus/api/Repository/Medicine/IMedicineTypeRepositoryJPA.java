package bkd.src.salus.api.Repository.Medicine;

import bkd.src.salus.api.Domain.Entity.Medicine.MedicineType;
import bkd.src.salus.api.Domain.Entity.Patient.PatientAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMedicineTypeRepositoryJPA extends JpaRepository<MedicineType, Integer> {

    @Query("""
            SELECT m
            FROM MedicineType m
            WHERE m.Name = :name
            """)
    MedicineType findTypeByName(String name);
}
