package bkd.src.salus.communicator.Application.RabbitMQ;

import bkd.src.salus.communicator.Domain.Entity.RabbitMQ.RabbitQueue;
import bkd.src.salus.communicator.Domain.Interface.Application.RabbitMQ.IMessageSender;
import bkd.src.salus.communicator.Repository.SQL.RabbitMQ.IRabbitQueueRepositoryJPA;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSender implements IMessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final IRabbitQueueRepositoryJPA queueRepositoryJPA;

    @Autowired
    public MessageSender(RabbitTemplate rabbitTemplate, IRabbitQueueRepositoryJPA queueRepositoryJPA) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueRepositoryJPA = queueRepositoryJPA;
    }

    @Override
    public void SendMessageOnExchange(String message, String exchange) {
        RabbitQueue queue = queueRepositoryJPA.findByExchange(exchange);
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey().toString(), message);
    }
}
