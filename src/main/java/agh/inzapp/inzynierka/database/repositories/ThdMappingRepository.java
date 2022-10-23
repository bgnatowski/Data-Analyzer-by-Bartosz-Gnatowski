package agh.inzapp.inzynierka.database.repositories;

import agh.inzapp.inzynierka.database.mappings.ThdMapping;
import agh.inzapp.inzynierka.database.mappings.ThdMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThdMappingRepository extends JpaRepository<ThdMapping, ThdMappingId> {
}