package bkd.src.salus.communicator.Application.RabbitMQ;

import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationRequest;
import bkd.src.salus.communicator.Domain.Interface.Application.Notification.IInitializeNotificationHandler;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    private final Gson objectMap;
    private final IInitializeNotificationHandler notificationHandler;

    @Autowired
    public MessageReceiver(IInitializeNotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
        objectMap = new Gson();
    }

    @RabbitListener(queues = { "notificator-communicator-queue" })
    public void receiveNotificationRequest(@Payload Message message) {
        String obj = new String(message.getBody());
        System.out.println("Received message on RabbitMQ: " + obj);
        MedicineNotificationRequest notification = objectMap.fromJson(obj, MedicineNotificationRequest.class);
        notificationHandler.MessageProcess(notification);
    }
}
