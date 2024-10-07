package bkd.src.salus.api.Application.Service.RabbitMQ;

import bkd.src.salus.api.Domain.DTO.Medicine.MedicineDecrementRequest;
import bkd.src.salus.api.Domain.Interface.Application.Medicine.IMedicineOperator;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMessageReceiver {

    private final Gson objectMap;
    private final IMedicineOperator medicineOperator;

    @Autowired
    public RabbitMessageReceiver(IMedicineOperator medicineOperator) {
        this.medicineOperator = medicineOperator;
        objectMap = new Gson();
    }

    @RabbitListener(queues = { "mqtt-medicine-notification-response-queue" })
    public void receiveMqttNotificationResponse(@Payload Message message) {
        String obj = new String(message.getBody());
        System.out.println("Received message on RabbitMQ: " + obj);
        MedicineDecrementRequest decrementRequest = objectMap.fromJson(obj, MedicineDecrementRequest.class);
        medicineOperator.DecrementMedicine(decrementRequest.getMedicineId(), decrementRequest.getUserId(), decrementRequest.getQuantity());
    }
}
