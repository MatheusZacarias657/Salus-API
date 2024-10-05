package bkd.src.salus.api.Domain.Entity.NoSQL.Topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "MQTT_Topics")
@TypeAlias("MqttTopic")
public class MqttTopic {

    @Id
    private String id;

    private String EspId;

    private List<String> Topics;

    public MqttTopic(String espId, List<String> topics){
        this.EspId = espId;
        this.Topics = topics;
    }

    public void AddTopic(String topic){
        this.Topics.add(topic);
    }
}
