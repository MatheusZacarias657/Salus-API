package bkd.src.salus.communicator.Repository.NoSQL.Topic;

import bkd.src.salus.communicator.Domain.Entity.NoSQL.Topic.MqttTopic;
import bkd.src.salus.communicator.Domain.Interface.Repository.ITopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TopicRepositoryMR implements ITopicRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<String> findAllUniqueTopics() {
        return mongoTemplate.findDistinct(new Query(), "Topic", MqttTopic.class, String.class);
    }

    public List<String> findTopicsByUserIdAndHardwareId(int userId, String hardwareId){
        Query query = new Query();
        query.addCriteria(Criteria.where("UserId").is(userId)
                .andOperator(
                        new Criteria().orOperator(
                                Criteria.where("HardwareId").is(null),
                                Criteria.where("HardwareId").is(hardwareId)
                        )
                )
        );

        List<MqttTopic> topic = mongoTemplate.find(query, MqttTopic.class);

        return  topic.stream().map(MqttTopic::getTopic).collect(Collectors.toList());
    }
}

