package bkd.src.salus.notificator.Repository.NoSQL.Mongo;

import bkd.src.salus.notificator.Domain.Entity.NoSQL.MedicineConsumeLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMedicineConsumeLogRepositoryMR extends MongoRepository<MedicineConsumeLog, String> {
}
