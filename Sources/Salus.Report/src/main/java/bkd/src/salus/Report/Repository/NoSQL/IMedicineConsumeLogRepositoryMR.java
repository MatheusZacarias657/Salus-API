package bkd.src.salus.Report.Repository.NoSQL;

import bkd.src.salus.Report.Domain.Entity.NoSQL.MedicineConsumeLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IMedicineConsumeLogRepositoryMR extends MongoRepository<MedicineConsumeLog, String> {

    @Query("{ 'Username': ?0 }")
    List<MedicineConsumeLog> findByNameUsingQuery(String username);
}
