package bkd.src.salus.communicator.Domain.Interface.Application.RabbitMQ;

public interface IRabbitMessageSender {
    void SendMessageOnExchange(String message, String exchange);
    void SendMessageOnExchangeAsync(String message, String exchange, int delayInMillis);
}