package bkd.src.salus.api.Repository.NoSQL.MedicineLog;

import bkd.src.salus.api.Domain.Entity.NoSQL.MedicineConsume.MedicineConsumeLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMedicineConsumeLogRepositoryMR extends MongoRepository<MedicineConsumeLog, String> {

    @Query("{ 'Username' : ?0, 'LogAt' : { $gte: ?1, $lte: ?2 } }")
    Optional<List<MedicineConsumeLog>> findByUsernameAndLogAtBetween(String username, LocalDateTime start, LocalDateTime end);
}
