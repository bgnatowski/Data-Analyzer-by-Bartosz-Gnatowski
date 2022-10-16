package agh.inzapp.inzynierka.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordsMappingRepository extends JpaRepository<RecordsMapping, RecordsMappingId> {

}