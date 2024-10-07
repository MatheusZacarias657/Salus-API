package bkd.src.salus.api.Domain.Entity.SQL.RabbitMQ;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

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
    private boolean Delayed;
}
