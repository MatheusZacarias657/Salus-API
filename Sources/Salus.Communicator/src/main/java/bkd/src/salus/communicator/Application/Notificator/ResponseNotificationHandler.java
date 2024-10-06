package bkd.src.salus.communicator.Application.Notificator;

import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationRequest;
import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationResponse;
import bkd.src.salus.communicator.Domain.DTO.Notification.BaseNotification;
import bkd.src.salus.communicator.Domain.DTO.Notification.ConsumeMedicineConfirmation;
import bkd.src.salus.communicator.Domain.DTO.Notification.ConsumeMedicineNotification;
import bkd.src.salus.communicator.Domain.DTO.Notification.NextNotification;
import bkd.src.salus.communicator.Domain.Interface.Application.MQTT.IMqttManager;
import bkd.src.salus.communicator.Domain.Interface.Application.Notification.IResponseNotificationHandler;
import bkd.src.salus.communicator.Domain.Interface.Repository.IRedisStackManager;
import bkd.src.salus.communicator.Domain.Interface.Repository.ITopicRepository;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.graalvm.collections.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResponseNotificationHandler implements IResponseNotificationHandler {

    private final IRedisStackManager redisStackManager;
    private final ITopicRepository topicRepository;
    private final Gson objectMap;

    @Autowired
    public ResponseNotificationHandler(IRedisStackManager redisStackManager, ITopicRepository topicRepository) {
        this.redisStackManager = redisStackManager;
        this.topicRepository = topicRepository;
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
        List<MedicineNotificationRequest> removeds = new ArrayList<>();
        MedicineNotificationResponse notificationResponse = confirmation.getParams();
        List<String> topics = topicRepository.findTopicsByUserIdAndHardwareId(notificationResponse.getUserId(),notificationResponse.getHardwareId());

        for (String topic : topics){

            String key = topic.contains("drawer") ? notificationResponse.getHardwareId() : String.valueOf(notificationResponse.getUserId());

            if(!topic.contains("drawer")){
                removeds.add(objectMap.fromJson(redisStackManager.PopFirst(key), MedicineNotificationRequest.class));

                if(redisStackManager.KeyExists(key)){
                    nextNotifications.add(new NextNotification(redisStackManager.CatchFirst(key), topic));
                }
            }
            else {
                Pair<Integer,MedicineNotificationRequest> itemOnQueue =  FindReference(key, notificationResponse);

                if (itemOnQueue != null){
                    removeds.add(itemOnQueue.getRight());

                    if(itemOnQueue.getLeft() == 0 && redisStackManager.KeyExists(key)){
                        nextNotifications.add(new NextNotification(redisStackManager.CatchFirst(key), topic));
                    }
                }
            }

        }

        Set<MedicineNotificationRequest> uniqueRemoveds = new HashSet<>(removeds);
        topics.remove(topicResponse);

        for(MedicineNotificationRequest removed : uniqueRemoveds){
            MedicineNotificationResponse repeaterResponse = new MedicineNotificationResponse(removed.getHardwareId(), removed.getUserId(), removed.getMedicineId());
            ConsumeMedicineConfirmation repeaterConfirmation = new ConsumeMedicineConfirmation("Repeater", repeaterResponse);

            for(String topic : topics){
                nextNotifications.add(new NextNotification(objectMap.toJson(repeaterConfirmation), topic));
            }
        }

        int j = 0;
        //TODO: notificar o monolito qual medicamento deve ser tirado na medida

        return nextNotifications;
    }

    private Pair<Integer, MedicineNotificationRequest> FindReference(String key, MedicineNotificationResponse notificationResponse){

        List<String> hardwareQueue = redisStackManager.GetAllValues(key);

        for (int i=0; i< hardwareQueue.size(); i++){
            String jsonItem = hardwareQueue.get(i);
            MedicineNotificationRequest request = objectMap.fromJson(jsonItem, MedicineNotificationRequest.class);

            if(request.getUserId() == notificationResponse.getUserId()
               && request.getMedicineId() == notificationResponse.getMedicineId()){
                hardwareQueue.remove(i);

                if(!hardwareQueue.isEmpty()){
                    redisStackManager.PushAllValues(key, hardwareQueue);
                }
                else {
                    redisStackManager.DeleteList(key);
                }

                return Pair.create(i, request);
            }
        }

        return null;
    }
}
