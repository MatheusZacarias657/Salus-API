package bkd.src.salus.api.Repository.NoSQL.Mongo.TreatmentRegisterLog;

import bkd.src.salus.api.Domain.Entity.NoSQL.TreatmentRegisterLog.TreatmentRegisterLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITreatmentRegisterLogRepositoryMR extends MongoRepository<TreatmentRegisterLog, String> {

}
