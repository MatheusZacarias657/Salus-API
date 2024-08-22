package api.src.salus.api.Repository.Patient;

import api.src.salus.api.Domain.Entity.Patient.PatientAllergy;
import api.src.salus.api.Domain.Entity.Patient.PatientDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPatientAllergyRepositoryJPA extends JpaRepository<PatientAllergy, Integer> {

    @Query("""
            SELECT p
            FROM PatientAllergy p
            WHERE p.Patient.User.Id = :userId
            """)
    List<PatientAllergy> findPatientAllergiesByUserId(int userId);

    @Query("""
            SELECT p
            FROM PatientAllergy p
            WHERE p.Patient.User.Id = :userId
            AND p.Id = :diseaseId
            """)
    PatientAllergy findPatientAllergiesByUserIdAndAllergyId(int userId, int diseaseId);
}
