package bkd.src.salus.communicator.Application.MQTT;

import bkd.src.salus.communicator.Domain.DTO.Notification.NextNotification;
import bkd.src.salus.communicator.Domain.Interface.Application.MQTT.IMqttManager;
import bkd.src.salus.communicator.Domain.Interface.Application.Notification.IResponseNotificationHandler;
import bkd.src.salus.communicator.Domain.Interface.Repository.IRedisStackManager;
import bkd.src.salus.communicator.Domain.Interface.Repository.ITopicRepository;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class MqttManager implements IMqttManager {

    @Value("${api.mqtt.broker}")
    private String mqttBroker;

    private static MqttAsyncClient mqttClient;
    private final ITopicRepository topicRepositoryMR;
    private final IRedisStackManager redisStackManager;
    private final IResponseNotificationHandler notificationHandler;

    @Autowired
    public MqttManager(ITopicRepository topicRepositoryMR, IRedisStackManager redisStackManager, IResponseNotificationHandler notificationHandler) throws MqttException {
        this.topicRepositoryMR = topicRepositoryMR;
        this.redisStackManager = redisStackManager;
        this.notificationHandler = notificationHandler;
        redisStackManager.DeleteList("current_topics");
    }

    @PostConstruct
    public void init() throws MqttException {
        InitializeMQTT();
    }

    private void Connect() throws MqttException {
        UUID uuid = UUID.randomUUID();
        String tempDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence persistence = new MqttDefaultFilePersistence(tempDir);
        System.out.println("Broker: " + mqttBroker);
        mqttClient = new MqttAsyncClient(mqttBroker, uuid.toString(), persistence);
        MqttConnectOptions mqttOptions = new MqttConnectOptions();
        mqttOptions.setCleanSession(true);
        mqttClient.connect(mqttOptions).waitForCompletion();
    }

    public void AddSubscribers(List<String> subscribers) throws MqttException {
        for(String subscriber : subscribers){
            mqttClient.subscribe(subscriber, 1);
            redisStackManager.AddObjectToList("current_topics", subscriber);
        }
    }

    private void InitializeMQTT() throws MqttException {
        Connect();
        List<String> topics = topicRepositoryMR.findAllUniqueTopics();
        AddSubscribers(topics);
    }

    public void SendMessage(String message, String topic) throws MqttException {
        MqttMessage mqttMsg = new MqttMessage(message.getBytes());
        mqttMsg.setQos(1);
        mqttClient.publish(topic, mqttMsg);
    }

    @Scheduled(fixedDelay = 10)
    public void ManageMessages() throws MqttException {
        if (mqttClient.isConnected()) {
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.printf("Received on %s this message: %s%n", topic, new String(message.getPayload()));
                    List<NextNotification> nextMessages = notificationHandler.MessageProcess(new String(message.getPayload()), topic);

                    for(NextNotification nextMessage : nextMessages){
                        SendMessage(nextMessage.getMessage(), nextMessage.getTopic());
                    }
                }
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection is lost: " + cause.getMessage());
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Message publish is complete: " + token.isComplete());
                }
            });
        }
        else {
            redisStackManager.DeleteList("current_topics");
            InitializeMQTT();
            System.out.println("Reconected on MQTT");
        }
    }
}