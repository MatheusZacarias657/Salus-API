package api.src.salus.api.Domain.Interface.Application.User;

import api.src.salus.api.Domain.DTO.User.UserNotificationDTO;
import org.springframework.transaction.annotation.Transactional;

public interface IUserNotificationService {
    @Transactional
    void RegisterNotification(UserNotificationDTO userNotification);

    @Transactional
    void DeleteNotification(UserNotificationDTO userNotification);
}
