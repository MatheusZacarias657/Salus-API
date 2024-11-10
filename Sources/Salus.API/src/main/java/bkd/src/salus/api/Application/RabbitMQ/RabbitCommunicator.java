package bkd.src.salus.api.Application.RabbitMQ;

import bkd.src.salus.api.Domain.DTO.Treatment.RequestTreatmentScheduler;
import bkd.src.salus.api.Domain.Interface.Application.RabbitMQ.IRabbitCommunicator;
import bkd.src.salus.api.Domain.Interface.Application.RabbitMQ.IRabbitCommunicatorTreatment;
import bkd.src.salus.api.Domain.Interface.Application.RabbitMQ.IRabbitMessageSender;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitCommunicator implements IRabbitCommunicator, IRabbitCommunicatorTreatment {
    private final IRabbitMessageSender messageSender;
    private final Gson objectMap;

    @Autowired
    public RabbitCommunicator(IRabbitMessageSender messageSender) {
        this.messageSender = messageSender;
        this.objectMap = new Gson();
    }

    @Override
    public void AddSubscriber(List<String> literners){
        messageSender.SendMessageOnExchange(objectMap.toJson(literners), "mqtt-listener-register-exchange");
    }

    @Override
    public void InitializeTreatmentNotification(int treatmentId){
        RequestTreatmentScheduler treatmentScheduler = new RequestTreatmentScheduler(treatmentId);
        messageSender.SendMessageOnExchange(objectMap.toJson(treatmentScheduler), "request-treatment-notification-exchange");
    }
}
