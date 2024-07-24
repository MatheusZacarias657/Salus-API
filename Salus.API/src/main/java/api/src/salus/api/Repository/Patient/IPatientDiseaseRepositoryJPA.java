package api.src.salus.api.Repository.Patient;

import api.src.salus.api.Domain.Entity.Patient.PatientDisease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatientDiseaseRepositoryJPA extends JpaRepository<PatientDisease, Integer> {

}
