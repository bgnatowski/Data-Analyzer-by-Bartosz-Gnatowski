package agh.inzapp.inzynierka.repositories;

import agh.inzapp.inzynierka.entities.PQDataDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PQRepository extends JpaRepository<PQDataDb, Long> {

}
