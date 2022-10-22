package agh.inzapp.inzynierka.database.repositories;

import agh.inzapp.inzynierka.database.models.RecordsMapping;
import agh.inzapp.inzynierka.database.models.RecordsMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordsMappingRepository extends JpaRepository<RecordsMapping, RecordsMappingId> {
}