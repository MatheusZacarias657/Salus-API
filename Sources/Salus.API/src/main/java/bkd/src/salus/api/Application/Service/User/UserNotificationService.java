package bkd.src.salus.api.Application.Service.User;

import bkd.src.salus.api.Domain.DTO.User.UserNotificationDTO;
import bkd.src.salus.api.Domain.DTO.User.UserPreferenceResponseDetailing;
import bkd.src.salus.api.Domain.Entity.User.UserNotification;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserNotificationService;
import bkd.src.salus.api.Repository.User.IUserNotificationRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserNotificationService implements IUserNotificationService {

    private final IUserNotificationRepositoryJPA repository;

    @Autowired
    public UserNotificationService(IUserNotificationRepositoryJPA repository){
        this.repository = repository;
    }

    @Override
    @Transactional
    public void RegisterNotification(UserNotificationDTO userNotification){
        UserNotification entity = new UserNotification(userNotification);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void DeleteNotification(UserNotificationDTO userNotification){
        UserNotification entity = repository.getUserNotificationIdByUserIdAndChannelId(userNotification.getUserId(), userNotification.getChannelId());
        repository.delete(entity);
    }
}
