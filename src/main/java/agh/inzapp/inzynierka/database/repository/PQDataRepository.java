package agh.inzapp.inzynierka.database.repository;

import agh.inzapp.inzynierka.database.dbmodels.PQDataDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PQDataRepository extends JpaRepository<PQDataDb, Long> {

}
