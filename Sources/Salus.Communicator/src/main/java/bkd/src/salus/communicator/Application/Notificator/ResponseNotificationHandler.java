package bkd.src.salus.communicator.Application.Notificator;

import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineDecrementRequest;
import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationRequest;
import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationResponse;
import bkd.src.salus.communicator.Domain.DTO.Notification.BaseNotification;
import bkd.src.salus.communicator.Domain.DTO.Notification.ConsumeMedicineConfirmation;
import bkd.src.salus.communicator.Domain.DTO.Notification.NextNotification;
import bkd.src.salus.communicator.Domain.DTO.Queue.QueueItem;
import bkd.src.salus.communicator.Domain.Interface.Application.ILogMedicine;
import bkd.src.salus.communicator.Domain.Interface.Application.Notification.IResponseNotificationHandler;
import bkd.src.salus.communicator.Domain.Interface.Application.RabbitMQ.IRabbitMessageSender;
import bkd.src.salus.communicator.Domain.Interface.Repository.IRedisStackManager;
import bkd.src.salus.communicator.Domain.Interface.Repository.ITopicRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResponseNotificationHandler implements IResponseNotificationHandler {

    private final IRedisStackManager redisStackManager;
    private final ITopicRepository topicRepository;
    private final Gson objectMap;
    private final IRabbitMessageSender messageSender;
    private final ILogMedicine logMedicine;

    @Autowired
    public ResponseNotificationHandler(IRedisStackManager redisStackManager, ITopicRepository topicRepository, IRabbitMessageSender messageSender, ILogMedicine logMedicine) {
        this.redisStackManager = redisStackManager;
        this.topicRepository = topicRepository;
        this.messageSender = messageSender;
        this.logMedicine = logMedicine;
        this.objectMap = new Gson();
    }

    public List<NextNotification> MessageProcess(String notification, String topicResponse) {

        BaseNotification baseNotification = objectMap.fromJson(notification, BaseNotification.class);

        switch (baseNotification.getAction()){
            case "ConsumeConfirmation":
                return ProcessConsume(objectMap.fromJson(notification, ConsumeMedicineConfirmation.class), topicResponse);

            case "GenericAction":
                break;

            default:
                return new ArrayList<>();
        }

        return new ArrayList<>();
    }

    private List<NextNotification> ProcessConsume(ConsumeMedicineConfirmation confirmation, String topicResponse){
        List<NextNotification> nextNotifications = new ArrayList<>();
        List<MedicineNotificationRequest> removed = new ArrayList<>();
        MedicineNotificationResponse notificationResponse = confirmation.getParams();
        List<String> topics = topicRepository.findTopicsByUserIdAndHardwareId(notificationResponse.getUserId(),notificationResponse.getHardwareId());
        logMedicine.LogConsume(confirmation.getParams().getUserId(), confirmation.getParams().getMedicineId(), confirmation.getParams().getTreatmentId(), "Consumido");

        for (String topic : topics){

            String key = topic.contains("drawer") ? notificationResponse.getHardwareId() : String.valueOf(notificationResponse.getUserId());
            QueueItem itemOnQueue =  FindReference(key, notificationResponse);

            if (itemOnQueue != null){
                removed.add(itemOnQueue.getMedicine());

                if(itemOnQueue.getPosition() == 0 && redisStackManager.KeyExists(key) && (topic.contains("drawer") && topic.contains("request"))){
                    nextNotifications.add(new NextNotification(redisStackManager.CatchFirst(key), topic));
                }
            }
        }

        Set<MedicineNotificationRequest> uniqueRemoved = new HashSet<>(removed);
        topics.remove(topicResponse);

        for(MedicineNotificationRequest removedNotification : uniqueRemoved){
            MedicineNotificationResponse repeaterResponse = new MedicineNotificationResponse(removedNotification.getHardwareId(), removedNotification.getUserId(), removedNotification.getMedicineId());
            ConsumeMedicineConfirmation repeaterConfirmation = new ConsumeMedicineConfirmation("Repeater", repeaterResponse);

            for(String topic : topics){

                if(!topic.contains("response")){
                    nextNotifications.add(new NextNotification(objectMap.toJson(repeaterConfirmation), topic));
                }
            }

            MedicineDecrementRequest medicineDecrement = new MedicineDecrementRequest(removedNotification.getUserId(), removedNotification.getMedicineId(), removedNotification.getQuantity());
            messageSender.SendMessageOnExchange(objectMap.toJson(medicineDecrement), "mqtt-medicine-notification-response-exchange");
        }


        return nextNotifications;
    }

    private QueueItem FindReference(String key, MedicineNotificationResponse notificationResponse){

        List<String> targetQueue = redisStackManager.GetAllValues(key);

        for (int i=0; i< targetQueue.size(); i++){
            String jsonItem = targetQueue.get(i);
            MedicineNotificationRequest request = objectMap.fromJson(jsonItem, MedicineNotificationRequest.class);

            if(request.getUserId() == notificationResponse.getUserId()
               && request.getMedicineId() == notificationResponse.getMedicineId()){
                targetQueue.remove(i);

                if(!targetQueue.isEmpty()){
                    redisStackManager.PushAllValues(key, targetQueue);
                }
                else {
                    redisStackManager.DeleteList(key);
                }

                return new QueueItem(i, request);
            }
        }

        return null;
    }
}
