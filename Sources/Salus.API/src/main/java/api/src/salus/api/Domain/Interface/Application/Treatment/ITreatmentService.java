package api.src.salus.api.Domain.Interface.Application.Treatment;

import api.src.salus.api.Domain.DTO.Treatment.CompleteDetailingTreatment;
import api.src.salus.api.Domain.DTO.Treatment.CompleteRegisterTreatment;
import api.src.salus.api.Domain.DTO.Treatment.DetailingTreatmentDTO;
import api.src.salus.api.Domain.DTO.Treatment.RegisterTreatmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITreatmentService {
    CompleteDetailingTreatment Create(CompleteRegisterTreatment register, int userId);

    Page<DetailingTreatmentDTO> FindAll(int userId, Pageable pageable);

    CompleteDetailingTreatment Find(int treatmentId, int userId, Pageable pageable);

    void Delete(int treatmentId, int userId);
}
