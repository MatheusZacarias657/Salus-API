package bkd.src.salus.api.Repository.NoSQL.MedicineLog;

import bkd.src.salus.api.Domain.Entity.NoSQL.MedicineConsume.MedicineConsumeLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMedicineConsumeLogRepositoryMR extends MongoRepository<MedicineConsumeLog, String> {
}
