package bkd.src.salus.api.Repository.SQL.User;

import bkd.src.salus.api.Domain.Entity.SQL.User.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserNotificationRepositoryJPA extends JpaRepository<UserNotification, Integer> {

    @Query("""
            SELECT u
            FROM UserNotification u
            WHERE u.UserId = :userId
            AND u.ChannelId = :channelId
            """)
    UserNotification getUserNotificationIdByUserIdAndChannelId(int userId, int channelId);
}
