package bkd.src.salus.api.Domain.Interface.Application.User;

import bkd.src.salus.api.Domain.DTO.User.Notification.UserChannelResponse;
import org.springframework.transaction.annotation.Transactional;

public interface IUserNotificationService {

    UserChannelResponse RegisterNotification(int channelId, int userId);
    void DeleteNotification(int channelId, int userId);
    UserChannelResponse CaptureAll(int userId);
}
