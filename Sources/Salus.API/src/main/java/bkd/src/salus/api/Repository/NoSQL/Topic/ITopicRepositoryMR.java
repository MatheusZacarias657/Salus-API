package bkd.src.salus.api.Repository.NoSQL.Topic;

import bkd.src.salus.api.Domain.Entity.NoSQL.Topic.MqttTopic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ITopicRepositoryMR extends MongoRepository<MqttTopic, String> {
}
