package bkd.src.salus.api.Repository.SQL.Patient;

import bkd.src.salus.api.Domain.Entity.SQL.Patient.PatientAllergy;
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
            AND p.Id = :allergyId
            """)
    PatientAllergy findPatientAllergiesByUserIdAndAllergyId(int userId, int allergyId);

    @Query("""
            SELECT p
            FROM PatientAllergy p
            WHERE p.Patient.User.Id = :userId
            AND p.Allergy = :allergy
            """)
    PatientAllergy findPatientAllergiesByUserIdAndName(int userId, String allergy);
}
