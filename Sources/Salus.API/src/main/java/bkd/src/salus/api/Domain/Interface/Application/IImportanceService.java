package bkd.src.salus.api.Domain.Interface.Application;

import bkd.src.salus.api.Domain.DTO.Importance.DetailingImportanceDTO;
import bkd.src.salus.api.Domain.DTO.Importance.RegisterImportanceDTO;
import java.util.List;

public interface IImportanceService {
    DetailingImportanceDTO Create(RegisterImportanceDTO register, int userId);

    List<DetailingImportanceDTO> FindAll(int userId);

    void RemoveMedicine(int importanceId, int userId);
}
