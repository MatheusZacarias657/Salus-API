package bkd.src.salus.communicator.Application.MQTT;

import bkd.src.salus.communicator.Domain.Interface.Application.MQTT.IMqttManager;
import bkd.src.salus.communicator.Domain.Interface.Repository.ITopicRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MqttManager implements IMqttManager {

    @Value("${comm.mqtt.broker}")
    private String mqttBroker;

    private static MqttClient mqttClient;
    private final ITopicRepository topicRepositoryMR;

    @Autowired
    public MqttManager(ITopicRepository topicRepositoryMR) throws MqttException {
        this.topicRepositoryMR = topicRepositoryMR;
        mqttBroker = "tcp://localhost:1883";
        InitializeMQTT();
        Run();
    }

    private void Connect() throws MqttException {
        UUID uuid = UUID.randomUUID();
        mqttClient = new MqttClient(mqttBroker, uuid.toString());
        MqttConnectOptions mqttOptions = new MqttConnectOptions();
        mqttClient.connect(mqttOptions);
    }

    public void AddSubscribers(List<String> subscribers) throws MqttException {
        for(String subscriber : subscribers){
            mqttClient.subscribe(subscriber, 1);
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

    private void ManageMessages() throws MqttException {
        if (mqttClient.isConnected()) {
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println(String.format("Received message: %s on %s", new String(message.getPayload()), topic));
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
            InitializeMQTT();
        }
    }

    private void Run() {
        try {
            while (true) {
                ManageMessages();
                Thread.sleep(1000);
            }
        } catch (InterruptedException | MqttException e) {
            e.printStackTrace();
        }
    }
}
