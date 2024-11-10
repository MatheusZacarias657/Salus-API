package bkd.src.salus.api.Application.RabbitMQ;

import bkd.src.salus.api.Domain.Entity.SQL.RabbitMQ.RabbitQueue;
import bkd.src.salus.api.Domain.Interface.Application.RabbitMQ.IRabbitMessageSender;
import bkd.src.salus.api.Repository.SQL.RabbitMQ.IRabbitQueueRepositoryJPA;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageSender implements IRabbitMessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final IRabbitQueueRepositoryJPA queueRepositoryJPA;

    @Autowired
    public RabbitMessageSender(RabbitTemplate rabbitTemplate, IRabbitQueueRepositoryJPA queueRepositoryJPA) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueRepositoryJPA = queueRepositoryJPA;
    }

    @Override
    public void SendMessageOnExchange(String message, String exchange) {
        RabbitQueue queue = queueRepositoryJPA.findByExchange(exchange);
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey().toString(), message);
    }
}
