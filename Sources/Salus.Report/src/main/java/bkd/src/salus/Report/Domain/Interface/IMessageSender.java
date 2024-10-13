package bkd.src.salus.Report.Domain.Interface;

public interface IMessageSender {
    void SendMessageOnExchange(String message, String exchange);
}
