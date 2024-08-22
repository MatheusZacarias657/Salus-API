package api.src.salus.api.Repository.Treatment;

import api.src.salus.api.Domain.Entity.Medicine.Medicine;
import api.src.salus.api.Domain.Entity.Treatment.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITreatmentRepositoryJPA extends JpaRepository<Treatment, Integer> {

}
