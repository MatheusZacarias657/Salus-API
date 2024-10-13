package bkd.src.salus.communicator.Repository.NoSQL.MedicineLog;

import bkd.src.salus.communicator.Domain.Entity.NoSQL.MedicineLog.MedicineConsumeLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMedicineConsumeLogRepositoryMR extends MongoRepository<MedicineConsumeLog, String> {
}
