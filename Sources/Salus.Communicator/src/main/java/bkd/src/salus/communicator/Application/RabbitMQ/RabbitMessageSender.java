package bkd.src.salus.communicator.Application.RabbitMQ;

import bkd.src.salus.communicator.Domain.Entity.SQL.RabbitMQ.RabbitQueue;
import bkd.src.salus.communicator.Domain.Interface.Application.RabbitMQ.IRabbitMessageSender;
import bkd.src.salus.communicator.Repository.SQL.RabbitMQ.IRabbitQueueRepositoryJPA;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitMessageSender implements IRabbitMessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final IRabbitQueueRepositoryJPA queueRepositoryJPA;

    @Autowired
    public RabbitMessageSender(RabbitTemplate rabbitTemplate, IRabbitQueueRepositoryJPA queueRepositoryJPA) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueRepositoryJPA = queueRepositoryJPA;
    }

    public void SendMessageOnExchange(String message, String exchange) {
        RabbitQueue queue = queueRepositoryJPA.findByExchange(exchange);
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey().toString(), message);
    }

    public void SendMessageOnExchangeAsync(String message, String exchange, int delayInMillis) {

        Map<String, Object> headers = new HashMap<>();
        headers.put("x-delay", delayInMillis);

        RabbitQueue queue = queueRepositoryJPA.findByExchange(exchange);

        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey().toString(), message, msg -> {
            msg.getMessageProperties().getHeaders().putAll(headers);
            return msg;
        });
    }
}
