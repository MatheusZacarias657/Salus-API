package bkd.src.salus.communicator;

import bkd.src.salus.communicator.Application.MQTT.MqttManager;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class CommunicatorApplication {

	public static void main(String[] args){
		Locale.setDefault(Locale.ENGLISH);
		SpringApplication.run(CommunicatorApplication.class, args);
    }
}
