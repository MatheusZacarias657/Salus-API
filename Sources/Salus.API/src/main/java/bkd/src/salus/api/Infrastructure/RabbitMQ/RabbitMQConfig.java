package bkd.src.salus.api.Infrastructure.RabbitMQ;

import bkd.src.salus.api.Domain.Entity.SQL.RabbitMQ.RabbitQueue;
import bkd.src.salus.api.Repository.SQL.RabbitMQ.IRabbitQueueRepositoryJPA;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

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
        rabbitAdmin.initialize(); // Ensure the RabbitAdmin is initialized
        return rabbitAdmin;
    }

    @Bean
    public List<Binding> ConfigureAllQueues(){
        List<RabbitQueue> queues = rabbitQueueRepository.findAll();
        List<Binding> bindings = new ArrayList<>();

        for(RabbitQueue queue : queues){
            bindings.add(BindQueues(queue.getQueue(), queue.getExchange(), queue.getRoutingKey().toString()));
        }

        return bindings;
    }

    private void InsertQueue(Queue queue, TopicExchange exchange, Binding binding){
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(binding);
    }

    private Binding BindQueues(String queueName, String exchangeName, String routingKey){
        Queue queue = new Queue(queueName, true);
        TopicExchange exchange = new TopicExchange(exchangeName);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        InsertQueue(queue, exchange, binding);

        return  binding;
    }
}
