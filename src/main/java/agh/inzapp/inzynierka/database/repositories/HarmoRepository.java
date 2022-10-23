package agh.inzapp.inzynierka.database.repositories;

import agh.inzapp.inzynierka.database.models.HarmoDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarmoRepository extends JpaRepository<HarmoDb, Long> {
}