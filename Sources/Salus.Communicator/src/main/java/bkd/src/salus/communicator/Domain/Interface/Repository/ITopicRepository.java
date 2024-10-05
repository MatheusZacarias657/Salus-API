package bkd.src.salus.communicator.Domain.Interface.Repository;

import java.util.List;

public interface ITopicRepository {
    List<String> findAllUniqueTopics();
}
