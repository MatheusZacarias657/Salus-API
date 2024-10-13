package bkd.src.salus.api.Application.Service.Resume;

import bkd.src.salus.api.Domain.DTO.Resume.DayResume;
import bkd.src.salus.api.Domain.DTO.Resume.HasPendency;
import bkd.src.salus.api.Domain.Interface.Application.IResumeService;
import bkd.src.salus.api.Domain.Interface.Repository.IRedisStackManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeService implements IResumeService {
    private final IRedisStackManager redisStackManager;

    @Autowired
    public ResumeService(IRedisStackManager redisStackManager) {
        this.redisStackManager = redisStackManager;
    }

    @Override
    public HasPendency CheckIfHasPendency(int userId){
        String key = String.valueOf(userId);
        return new HasPendency(redisStackManager.KeyExists(key));
    }

    @Override
    public DayResume GetResumeOfDay(int userId){
        return new DayResume();
    }
}
