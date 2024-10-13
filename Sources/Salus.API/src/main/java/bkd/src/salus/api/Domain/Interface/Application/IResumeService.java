package bkd.src.salus.api.Domain.Interface.Application;

import bkd.src.salus.api.Domain.DTO.Resume.DayResume;
import bkd.src.salus.api.Domain.DTO.Resume.HasPendency;

public interface IResumeService {
    HasPendency CheckIfHasPendency(int userId);

    DayResume GetResumeOfDay(int userId);
}
