package agh.inzapp.inzynierka.database.repositories;

import agh.inzapp.inzynierka.database.mappings.HarmonicsMapping;
import agh.inzapp.inzynierka.database.mappings.HarmonicsMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarmonicsMappingRepository extends JpaRepository<HarmonicsMapping, HarmonicsMappingId> {
}