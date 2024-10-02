package bkd.src.salus.api.Repository.Patient;

import bkd.src.salus.api.Domain.Entity.Patient.PatientAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPatientAddressRepositoryJPA extends JpaRepository<PatientAddress, Integer> {

    @Query("""
            SELECT p
            FROM PatientAddress p
            WHERE p.Patient.User.Id = :userId
            """)
    PatientAddress findPatientAddressByUserId(int userId);
}
