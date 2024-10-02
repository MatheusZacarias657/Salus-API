package api.src.salus.api.Domain.Interface.Application;

import api.src.salus.api.Domain.DTO.Importance.DetailingImportanceDTO;
import api.src.salus.api.Domain.DTO.Importance.RegisterImportanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IImportanceService {
    DetailingImportanceDTO Create(RegisterImportanceDTO register, int userId);

    Page<DetailingImportanceDTO> FindAll(int userId, Pageable pageable);

    void RemoveMedicine(int importanceId, int userId);
}
