package bkd.src.salus.api.Domain.Interface.Application.RabbitMQ;

import java.util.List;

public interface IRabbitCommunicator {
    void AddSubscriber(List<String> literners);
}
