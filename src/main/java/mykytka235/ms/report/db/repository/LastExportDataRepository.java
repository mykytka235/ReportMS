package mykytka235.ms.report.db.repository;

import mykytka235.ms.report.db.model.LastExportData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LastExportDataRepository extends MongoRepository<LastExportData, ObjectId> {

    Optional<LastExportData> findByCustomerId(String customerId);

}
