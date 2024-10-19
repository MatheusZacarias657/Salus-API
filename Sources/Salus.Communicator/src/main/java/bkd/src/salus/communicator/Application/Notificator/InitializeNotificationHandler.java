package bkd.src.salus.communicator.Application.Notificator;

import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationRequest;
import bkd.src.salus.communicator.Domain.DTO.Notification.ConsumeMedicineNotification;
import bkd.src.salus.communicator.Domain.Interface.Application.ILogMedicine;
import bkd.src.salus.communicator.Domain.Interface.Application.MQTT.IMqttManager;
import bkd.src.salus.communicator.Domain.Interface.Application.Notification.IInitializeNotificationHandler;
import bkd.src.salus.communicator.Domain.Interface.Repository.IRedisStackManager;
import bkd.src.salus.communicator.Domain.Interface.Repository.ITopicRepository;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InitializeNotificationHandler implements IInitializeNotificationHandler {

    private final IRedisStackManager redisStackManager;
    private final IMqttManager mqttManager;
    private final ITopicRepository topicRepository;
    private final Gson objectMap;
    private final ILogMedicine logMedicine;

    @Autowired
    public InitializeNotificationHandler(IRedisStackManager redisStackManager, IMqttManager mqttManager, ITopicRepository topicRepository, ILogMedicine logMedicine) {
        this.redisStackManager = redisStackManager;
        this.mqttManager = mqttManager;
        this.topicRepository = topicRepository;
        this.logMedicine = logMedicine;
        this.objectMap = new Gson();
    }

    @Override
    public void MessageProcess(MedicineNotificationRequest notificationRequest) {
        List<String> mongoTopics = topicRepository.findTopicsByUserIdAndHardwareId(notificationRequest.getUserId(), notificationRequest.getHardwareId());
        Set<String> uniqueSet = new HashSet<>(mongoTopics);
        List<String> topics = new ArrayList<>(uniqueSet);
        //logMedicine.LogConsume(notificationRequest.getUserId(), notificationRequest.getMedicineId(), "Solicitado");

        for (String topic : topics){
            if(!redisStackManager.DoesValueExistInList("current_topics", topic)){
                try{
                    mqttManager.AddSubscribers(Collections.singletonList(topic));
                }
                catch (MqttException ignored){ }
            }

            if(topic.contains("response")){
                continue;
            }

            if(topic.contains("drawer") && topic.contains("request")){
                TrySendToDrawer(notificationRequest.getHardwareId(), topic, notificationRequest);
            }
            else{
                SendToUser(String.valueOf(notificationRequest.getUserId()), topic, notificationRequest);
            }
        }
    }

    private void TrySendToDrawer(String key, String topic, MedicineNotificationRequest notificationRequest){
        if(!redisStackManager.KeyExists(key)){
            SendNotification(topic, notificationRequest);
        }

        redisStackManager.AddObjectToList(key, notificationRequest);
    }

    private void SendToUser(String key, String topic, MedicineNotificationRequest notificationRequest){
        SendNotification(topic, notificationRequest);
        redisStackManager.AddObjectToList(key, notificationRequest);
        //TODO: Inicia a contagem pra comunicar o supervisor
    }

    private void SendNotification(String topic, MedicineNotificationRequest notificationRequest){
        try{
            ConsumeMedicineNotification notification = new ConsumeMedicineNotification("RequestConsume", notificationRequest);
            mqttManager.SendMessage(objectMap.toJson(notification), topic);
        }
        catch (MqttException ignored){ }
    }
}
