package bkd.src.salus.communicator.Domain.Interface.Application.RabbitMQ;

public interface IMessageSender {
    void SendMessageOnExchange(String message, String exchange);
}
