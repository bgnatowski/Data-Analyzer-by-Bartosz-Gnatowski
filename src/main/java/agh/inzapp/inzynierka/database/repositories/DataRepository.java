package agh.inzapp.inzynierka.database.repositories;

import agh.inzapp.inzynierka.database.models.DataDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataDb, Long> {

}
