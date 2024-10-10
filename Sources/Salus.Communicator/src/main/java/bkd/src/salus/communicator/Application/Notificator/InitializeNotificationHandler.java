package bkd.src.salus.communicator.Application.Notificator;

import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationRequest;
import bkd.src.salus.communicator.Domain.DTO.Notification.ConsumeMedicineNotification;
import bkd.src.salus.communicator.Domain.Interface.Application.MQTT.IMqttManager;
import bkd.src.salus.communicator.Domain.Interface.Application.Notification.IInitializeNotificationHandler;
import bkd.src.salus.communicator.Domain.Interface.Repository.IRedisStackManager;
import bkd.src.salus.communicator.Domain.Interface.Repository.ITopicRepository;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class InitializeNotificationHandler implements IInitializeNotificationHandler {

    private final IRedisStackManager redisStackManager;
    private final IMqttManager mqttManager;
    private final ITopicRepository topicRepository;
    private final Gson objectMap;

    @Autowired
    public InitializeNotificationHandler(IRedisStackManager redisStackManager, IMqttManager mqttManager, ITopicRepository topicRepository) {
        this.redisStackManager = redisStackManager;
        this.mqttManager = mqttManager;
        this.topicRepository = topicRepository;
        this.objectMap = new Gson();
    }

    @Override
    public void MessageProcess(MedicineNotificationRequest notificationRequest) {

        List<String> topics = topicRepository.findTopicsByUserIdAndHardwareId(notificationRequest.getUserId(), notificationRequest.getHardwareId());

        for (String topic : topics){
            if(!redisStackManager.DoesValueExistInList("current_topics", topic)){
                try{
                    mqttManager.AddSubscribers(Collections.singletonList(topic));
                }
                catch (MqttException ignored){ }
            }

            String key = topic.contains("drawer") ? notificationRequest.getHardwareId() : String.valueOf(notificationRequest.getUserId());
            TrySend(key, topic, notificationRequest);
        }
    }

    private void TrySend(String key, String topic, MedicineNotificationRequest notificationRequest){

        if(!redisStackManager.KeyExists(key)){
            try{
                ConsumeMedicineNotification notification = new ConsumeMedicineNotification("RequestConsume", notificationRequest);
                mqttManager.SendMessage(objectMap.toJson(notification), topic);
            }
            catch (MqttException ignored){ }
        }

        redisStackManager.AddObjectToList(key, notificationRequest);
    }
}
