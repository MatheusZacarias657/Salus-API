package bkd.src.salus.communicator.Repository.Topic;

import bkd.src.salus.communicator.Domain.Interface.Repository.ITopicRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class TopicRepositoryImpl implements ITopicRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<String> findAllUniqueTopics() {
        UnwindOperation unwind = unwind("Topics");

        Aggregation aggregation = newAggregation(
                unwind,
                group("Topics").first("Topics").as("uniqueTopics")
        );

        AggregationResults<TopicResult> result = mongoTemplate.aggregate(aggregation, "MQTT_Topics", TopicResult.class);

        return result.getMappedResults().stream()
                .map(TopicResult::getUniqueTopics)
                .collect(Collectors.toList());
    }

    @Setter
    @Getter
    private static class TopicResult {
        private String uniqueTopics;
    }
}

