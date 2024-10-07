package bkd.src.salus.api.Domain.Interface.Application.RabbitMQ;

public interface IRabbitMessageSender {
    void SendMessageOnExchange(String message, String exchange);
}
