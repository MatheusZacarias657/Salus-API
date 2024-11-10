package bkd.src.salus.api.Application.Service.User;

import bkd.src.salus.api.Domain.DTO.User.Notification.ChannelResponseDetailing;
import bkd.src.salus.api.Domain.DTO.User.Notification.UserChannelResponse;
import bkd.src.salus.api.Domain.Entity.SQL.Channel.NotificationChannel;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserNotification;
import bkd.src.salus.api.Domain.Exception.ValidationException;
import bkd.src.salus.api.Domain.Interface.Application.User.IUserNotificationService;
import bkd.src.salus.api.Repository.SQL.Channel.IChannelRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserNotificationRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNotificationService implements IUserNotificationService {

    private final IUserNotificationRepositoryJPA repository;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final IChannelRepositoryJPA channelRepositoryJPA;

    @Autowired
    public UserNotificationService(IUserNotificationRepositoryJPA repository, IUserRepositoryJPA userRepositoryJPA, IChannelRepositoryJPA channelRepositoryJPA){
        this.repository = repository;
        this.userRepositoryJPA = userRepositoryJPA;
        this.channelRepositoryJPA = channelRepositoryJPA;
    }

    @Override
    public UserChannelResponse RegisterNotification(int channelId, int userId){
        UserNotification testEntity = repository.getUserNotificationIdByUserIdAndChannelId(userId, channelId);

        if(testEntity != null){
            throw new ValidationException( "This user already has this channel, please update if want to change data");
        }

        UserAccount user = userRepositoryJPA.findById(userId).get();
        NotificationChannel channel = channelRepositoryJPA.findById(channelId).get();
        UserNotification entity = new UserNotification(user, channel);
        repository.save(entity);

        List<ChannelResponseDetailing> channels = List.of(new ChannelResponseDetailing(entity));

        return  new UserChannelResponse(user.getLogin(), channels);
    }

    @Override
    public void DeleteNotification(int channelId, int userId){
        UserNotification entity = repository.getUserNotificationIdByUserIdAndChannelId(userId, channelId);
        repository.delete(entity);
    }

    @Override
    public UserChannelResponse CaptureAll(int userId){
        UserAccount user = userRepositoryJPA.findById(userId).get();
        List<UserNotification> entity = repository.getUserNotificationIdByUserId(userId);
        List<ChannelResponseDetailing> channels = entity.stream().map(ChannelResponseDetailing::new).toList();

        return  new UserChannelResponse(user.getLogin(), channels);
    }
}
