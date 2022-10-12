package agh.inzapp.inzynierka.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Repository
public interface DataRepository extends JpaRepository<DataDb, Long> {
//	@Query("select d from DataDb d where d.records = ?1")
//	Optional<DataDb> findInstance(Map records);



}
