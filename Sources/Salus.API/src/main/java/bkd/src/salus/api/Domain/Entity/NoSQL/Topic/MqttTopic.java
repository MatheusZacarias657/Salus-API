package bkd.src.salus.api.Domain.Entity.NoSQL.Topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "MQTT_Topics")
@TypeAlias("MqttTopic")
public class MqttTopic {

    @Id
    private String id;

    private String HardwareId;
    private int UserId;
    String Topic;

    public MqttTopic(int userId, String topic){
        this.UserId = userId;
        this.Topic = topic;
    }

    public MqttTopic(int userId, String topic, String hardwareId){
        this.UserId = userId;
        this.Topic = topic;
        this.HardwareId = hardwareId;
    }
}
