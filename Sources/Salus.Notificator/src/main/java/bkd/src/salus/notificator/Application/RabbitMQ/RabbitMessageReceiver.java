package bkd.src.salus.notificator.Application.RabbitMQ;

import bkd.src.salus.notificator.Domain.DTO.Medicine.MedicineRequestNotification;
import bkd.src.salus.notificator.Domain.DTO.Notification.AnswerableNotification;
import bkd.src.salus.notificator.Domain.DTO.Notification.MedicineNotificationRequest;
import bkd.src.salus.notificator.Domain.DTO.Treatment.RequestTreatmentScheduler;
import bkd.src.salus.notificator.Domain.Interface.Application.*;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMessageReceiver {

    private final Gson objectMap;
    private final IRabbitMessageSender rabbitMessageSender;
    private final IMedicineNotificationManager medicineNotificationManager;
    private final IAnswerableNotificationHandler answerableNotificationHandler;

    @Autowired
    public RabbitMessageReceiver(IRabbitMessageSender rabbitMessageSender, IMedicineNotificationManager medicineNotificationManager, IObjectJsonConverter objectJsonConverter, ILogMedicine logMedicine, IAnswerableNotificationHandler answerableNotificationHandler) {
        this.rabbitMessageSender = rabbitMessageSender;
        this.medicineNotificationManager = medicineNotificationManager;
        objectMap = objectJsonConverter.GetConverter();
        this.answerableNotificationHandler = answerableNotificationHandler;
    }

    @RabbitListener(queues = { "request-medicine-notification-queue" })
    public void receiveMqttNotificationRequest(@Payload Message message) {
        System.out.println("receive on medicine queue");
        String obj = new String(message.getBody());
        MedicineRequestNotification requestNotification = objectMap.fromJson(obj, MedicineRequestNotification.class);

        if(medicineNotificationManager.CheckNotificationToSend(requestNotification.getTreatmentMedicine(), requestNotification.getTreatment())){
            MedicineNotificationRequest request = medicineNotificationManager.CreateMqttPayload(requestNotification.getTreatment(), requestNotification.getTreatmentMedicine());
            System.out.printf("Sending Medicine %d now\n", request.getMedicineId());
            String messageToSend = objectMap.toJson(request);
            rabbitMessageSender.SendMessageOnExchange(messageToSend, "request-medicine-mqtt-notification-exchange");
        }

        medicineNotificationManager.ScheduleNextConsume(requestNotification.getTreatment(), requestNotification.getTreatmentMedicine());
        System.out.println("finish on medicine queue");
    }

    @RabbitListener(queues = { "request-treatment-notification-queue" })
    public void receiveNewNotificationRequest(@Payload Message message, Channel channel) throws IOException {
        System.out.println("receive on treatment queue");
        String obj = new String(message.getBody());
        RequestTreatmentScheduler treatmentScheduler = objectMap.fromJson(obj, RequestTreatmentScheduler.class);
        medicineNotificationManager.ScheduleTreatment(treatmentScheduler.getTreatmentId());
        System.out.println("finish on treatment queue");
    }

    @RabbitListener(queues = { "notification-medicine-answerable-queue" })
    public void receiveAnswerableNotification(@Payload Message message, Channel channel) throws IOException {
        System.out.println("receive on answerable queue");
        String obj = new String(message.getBody());
        AnswerableNotification answerableNotification = objectMap.fromJson(obj, AnswerableNotification.class);
        answerableNotificationHandler.CheckAndSend(answerableNotification);
    }

    //TODO: Action: GenericAction
    //TODO: param -> acao -> 1 (água)
}
