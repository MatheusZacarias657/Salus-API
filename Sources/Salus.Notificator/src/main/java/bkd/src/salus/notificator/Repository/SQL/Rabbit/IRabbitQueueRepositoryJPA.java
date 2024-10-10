package bkd.src.salus.notificator.Repository.SQL.Rabbit;

import bkd.src.salus.notificator.Domain.Entity.Rabbit.RabbitQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IRabbitQueueRepositoryJPA extends JpaRepository<RabbitQueue, UUID> {

    @Query("""
            SELECT r
            FROM RabbitQueue r
            WHERE r.Exchange = :exchange
            """)
    RabbitQueue findByExchange(String exchange);
}
