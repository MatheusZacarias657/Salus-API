package bkd.src.salus.api.Repository.NoSQL.AnswerableLastAccess;

import bkd.src.salus.api.Domain.Entity.NoSQL.AnswerableAccess.AnswerableLastAccessLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IAnswerableLastAccessLogRepositoryMR extends MongoRepository<AnswerableLastAccessLog, String> {

    @Query("{ 'Patient.Id' : ?0, 'Answerable.Id' : ?1 }")
    List<AnswerableLastAccessLog> findMostRecentAccess(int patientId, int answerableId, Sort sort);

    default AnswerableLastAccessLog findMostRecentAccess(int patientId, int answerableId) {
        List<AnswerableLastAccessLog> results = findMostRecentAccess(patientId, answerableId, Sort.by(Sort.Direction.DESC, "LogAt"));
        return results.isEmpty() ? null : results.get(0);
    }
}
