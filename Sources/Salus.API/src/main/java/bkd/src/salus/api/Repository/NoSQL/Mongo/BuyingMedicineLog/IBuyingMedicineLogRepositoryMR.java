package bkd.src.salus.api.Repository.NoSQL.Mongo.BuyingMedicineLog;

import bkd.src.salus.api.Domain.Entity.NoSQL.BuyingMedicineLog.BuyingMedicineLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IBuyingMedicineLogRepositoryMR extends MongoRepository<BuyingMedicineLog, String> {

}
