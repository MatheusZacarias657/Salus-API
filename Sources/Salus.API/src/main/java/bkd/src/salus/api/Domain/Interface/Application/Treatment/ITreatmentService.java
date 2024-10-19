package bkd.src.salus.api.Domain.Interface.Application.Treatment;

import bkd.src.salus.api.Domain.DTO.Treatment.CompleteDetailingTreatment;
import bkd.src.salus.api.Domain.DTO.Treatment.CompleteRegisterTreatment;
import bkd.src.salus.api.Domain.DTO.Treatment.DetailingTreatmentDTO;

import java.util.List;

public interface ITreatmentService {
    CompleteDetailingTreatment Create(CompleteRegisterTreatment register, int userId);

    List<DetailingTreatmentDTO> FindAll(int userId);

    CompleteDetailingTreatment Find(int treatmentId, int userId);

    void Delete(int treatmentId, int userId);
}
