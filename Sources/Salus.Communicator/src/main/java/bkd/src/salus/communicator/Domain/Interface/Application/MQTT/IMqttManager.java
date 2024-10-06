package bkd.src.salus.communicator.Domain.Interface.Application.MQTT;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public interface IMqttManager {
    void AddSubscribers(List<String> subscribers) throws MqttException;
    void SendMessage(String message, String topic) throws MqttException;
}
