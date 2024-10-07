package bkd.src.salus.communicator.Application.RabbitMQ;

import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationRequest;
import bkd.src.salus.communicator.Domain.Interface.Application.MQTT.IMqttManager;
import bkd.src.salus.communicator.Domain.Interface.Application.Notification.IInitializeNotificationHandler;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageReceiver {

    private final Gson objectMap;
    private final IInitializeNotificationHandler notificationHandler;
    private final IMqttManager mqttManager;

    @Autowired
    public MessageReceiver(IInitializeNotificationHandler notificationHandler, IMqttManager mqttManager) {
        this.notificationHandler = notificationHandler;
        this.mqttManager = mqttManager;
        objectMap = new Gson();
    }

    @RabbitListener(queues = { "request-medicine-mqtt-notification-queue" })
    public void receiveMqttNotificationRequest(@Payload Message message) {
        String obj = new String(message.getBody());
        System.out.println("Received message on RabbitMQ: " + obj);
        MedicineNotificationRequest notification = objectMap.fromJson(obj, MedicineNotificationRequest.class);
        notificationHandler.MessageProcess(notification);
    }

    @RabbitListener(queues = { "mqtt-listener-register-queue" })
    public void receiveMqtListenerRegister(@Payload Message message) {
        String obj = new String(message.getBody());
        System.out.println("Received message on RabbitMQ: " + obj);
        List<String> subscribers = objectMap.fromJson(obj, List.class);
        try {
            mqttManager.AddSubscribers(subscribers);
        } catch (MqttException ignored) { }
    }

    //TODO: Água
}
