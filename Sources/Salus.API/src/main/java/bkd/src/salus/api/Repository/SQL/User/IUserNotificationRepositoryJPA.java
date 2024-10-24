package bkd.src.salus.api.Repository.SQL.User;

import bkd.src.salus.api.Domain.Entity.SQL.User.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserNotificationRepositoryJPA extends JpaRepository<UserNotification, Integer> {

    @Query("""
            SELECT u
            FROM UserNotification u
            WHERE u.User.Id = :userId
            AND u.Channel.Id = :channelId
            """)
    UserNotification getUserNotificationIdByUserIdAndChannelId(int userId, int channelId);

    @Query("""
            SELECT u
            FROM UserNotification u
            WHERE u.User.Id = :userId
            """)
    List<UserNotification> getUserNotificationIdByUserId(int userId);
}
