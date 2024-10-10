package bkd.src.salus.notificator.Repository.SQL.Patient;

import bkd.src.salus.notificator.Domain.Entity.Patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPatientRepositoryJPA extends JpaRepository<Patient, Integer> {

    @Query("""
            SELECT p
            FROM Patient p
            WHERE p.User.Id = :userId
            """)
    Patient findPatientByUserId(int userId);
}
