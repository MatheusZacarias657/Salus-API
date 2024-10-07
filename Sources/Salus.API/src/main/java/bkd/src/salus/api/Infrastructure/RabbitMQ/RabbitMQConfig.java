package bkd.src.salus.api.Infrastructure.RabbitMQ;

import bkd.src.salus.api.Domain.Entity.SQL.RabbitMQ.RabbitQueue;
import bkd.src.salus.api.Repository.SQL.RabbitMQ.IRabbitQueueRepositoryJPA;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    private final IRabbitQueueRepositoryJPA rabbitQueueRepository;
    private RabbitAdmin rabbitAdmin;

    @Autowired
    public RabbitMQConfig(IRabbitQueueRepositoryJPA rabbitQueueRepository) {
        this.rabbitQueueRepository = rabbitQueueRepository;
    }

    @Bean
    public RabbitAdmin RabbitAdmin(ConnectionFactory connectionFactory) {
        rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.initialize();
        return rabbitAdmin;
    }

    @Bean
    public List<Binding> ConfigureAllQueues(){
        List<RabbitQueue> queues = rabbitQueueRepository.findAll();
        List<Binding> bindings = new ArrayList<>();

        for(RabbitQueue queue : queues){
            Binding binding = (!queue.isDelayed()) ?
                    BindSyncQueue(queue.getQueue(), queue.getExchange(), queue.getRoutingKey().toString()) :
                    BindAsyncQueue(queue.getQueue(), queue.getExchange(), queue.getRoutingKey().toString());

            bindings.add(binding);
        }

        return bindings;
    }

    private void InsertQueue(Queue queue, Exchange exchange, Binding binding){
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(binding);
    }

    private Binding BindAsyncQueue(String queueName, String exchangeName, String routingKey){
        Queue queue = new Queue(queueName, true);
        CustomExchange delayedExchange = DelayedExchange(exchangeName);
        Binding binding = BindingBuilder.bind(queue).to(delayedExchange).with(routingKey).noargs();
        InsertQueue(queue, delayedExchange, binding);

        return  binding;
    }

    private Binding BindSyncQueue(String queueName, String exchangeName, String routingKey){
        Queue queue = new Queue(queueName, true);
        TopicExchange exchange = new TopicExchange(exchangeName);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        InsertQueue(queue, exchange, binding);

        return  binding;
    }

    private CustomExchange DelayedExchange(String name) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");  // You can use 'fanout', 'topic', or 'direct'
        return new CustomExchange(name, "x-delayed-message", true, false, args);
    }
}
