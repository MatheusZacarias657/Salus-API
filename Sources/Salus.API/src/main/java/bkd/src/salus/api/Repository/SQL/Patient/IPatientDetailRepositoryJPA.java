package bkd.src.salus.api.Repository.SQL.Patient;

import bkd.src.salus.api.Domain.Entity.SQL.Patient.PatientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPatientDetailRepositoryJPA extends JpaRepository<PatientDetail, Integer> {

    @Query("""
            SELECT p
            FROM PatientDetail p
            WHERE p.Patient.User.Id = :userId
            """)
    PatientDetail findPatientDetailByUserId(int userId);
}
