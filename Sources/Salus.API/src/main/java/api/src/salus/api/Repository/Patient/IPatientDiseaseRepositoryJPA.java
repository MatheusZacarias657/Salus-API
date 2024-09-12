package api.src.salus.api.Repository.Patient;

import api.src.salus.api.Domain.Entity.Patient.PatientDetail;
import api.src.salus.api.Domain.Entity.Patient.PatientDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPatientDiseaseRepositoryJPA extends JpaRepository<PatientDisease, Integer> {

    @Query("""
            SELECT p
            FROM PatientDisease p
            WHERE p.Patient.User.Id = :userId
            """)
    List<PatientDisease> findPatientDiseasesByUserId(int userId);

    @Query("""
            SELECT p
            FROM PatientDisease p
            WHERE p.Patient.User.Id = :userId
            AND p.Id = :diseaseId
            """)
    PatientDisease findPatientDiseasesByUserIdAndDiseaseId(int userId, int diseaseId);
}
