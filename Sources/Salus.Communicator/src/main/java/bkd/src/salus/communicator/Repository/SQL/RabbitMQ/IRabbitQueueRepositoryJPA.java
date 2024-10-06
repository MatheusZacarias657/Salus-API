package bkd.src.salus.communicator.Repository.SQL.RabbitMQ;

import bkd.src.salus.communicator.Domain.Entity.RabbitMQ.RabbitQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRabbitQueueRepositoryJPA extends JpaRepository<RabbitQueue, UUID> {
}
