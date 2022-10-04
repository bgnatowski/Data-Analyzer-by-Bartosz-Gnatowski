package agh.inzapp.inzynierka.repository;

import agh.inzapp.inzynierka.database.PQDataDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PQRepository extends JpaRepository<PQDataDb, Long> {

}
