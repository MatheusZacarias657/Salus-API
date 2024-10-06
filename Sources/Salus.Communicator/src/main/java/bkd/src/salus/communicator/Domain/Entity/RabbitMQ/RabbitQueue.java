package bkd.src.salus.communicator.Domain.Entity.RabbitMQ;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "rabbitmq_queue")
@Entity(name = "RabbitQueue")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RabbitQueue {

    @Id
    @Column(name = "Routing_Key", columnDefinition = "uniqueidentifier")
    private UUID RoutingKey;

    private String Queue;

    private String Exchange;
}
