package bkd.src.salus.notificator.Domain.Interface.Application;

public interface IRabbitMessageSender {
    void SendMessageOnExchange(String message, String exchange);
    void SendMessageOnExchangeAsync(String message, String exchange, int delayInMillis);
}
