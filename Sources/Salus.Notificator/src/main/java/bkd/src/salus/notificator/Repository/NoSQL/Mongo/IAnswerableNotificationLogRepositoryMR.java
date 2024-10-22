package bkd.src.salus.notificator.Repository.NoSQL.Mongo;

import bkd.src.salus.notificator.Domain.Entity.NoSQL.AnswerableNotificationLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAnswerableNotificationLogRepositoryMR extends MongoRepository<AnswerableNotificationLog, String> {
}
