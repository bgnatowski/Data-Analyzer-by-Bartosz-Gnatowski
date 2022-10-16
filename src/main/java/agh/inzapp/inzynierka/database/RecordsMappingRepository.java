package agh.inzapp.inzynierka.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordsMappingRepository extends JpaRepository<RecordsMapping, RecordsMappingId> {
}