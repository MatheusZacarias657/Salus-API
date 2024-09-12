package api.src.salus.api.Domain.Interface.Application.Treatment;

import api.src.salus.api.Domain.DTO.Treatment.DetailingTreatmentDTO;
import api.src.salus.api.Domain.DTO.Treatment.RegisterTreatmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITreatmentService {
    DetailingTreatmentDTO Create(RegisterTreatmentDTO register, int userId);

    Page<DetailingTreatmentDTO> FindAll(int userId, Pageable pageable);

    DetailingTreatmentDTO Find(int treatmentId, int userId);

    void Delete(int treatmentId, int userId);
}
