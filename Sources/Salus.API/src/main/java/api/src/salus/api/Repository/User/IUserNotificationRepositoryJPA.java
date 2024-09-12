package api.src.salus.api.Repository.User;

import api.src.salus.api.Domain.Entity.User.UserNotification;
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
