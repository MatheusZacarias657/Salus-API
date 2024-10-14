package bkd.src.salus.api.Repository.SQL.Channel;

import bkd.src.salus.api.Domain.Entity.SQL.Channel.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IChannelRepositoryJPA extends JpaRepository<NotificationChannel, Integer> {
}
