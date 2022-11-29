package agh.inzapp.inzynierka.database.repositories;

import agh.inzapp.inzynierka.database.models.DataDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DataDb, Long> {
	List<DataDb> findAllByDateAfterAndDateBefore(LocalDateTime date, LocalDateTime date2);
	@Query(value = "select Date from data_db record WHERE record.date BETWEEN ?1 AND ?2", nativeQuery = true)
	List<Timestamp> findByDateBetween(LocalDateTime start, LocalDateTime end);
	@Query(value = "select id from data_db record WHERE record.date BETWEEN ?1 AND ?2", nativeQuery = true)
	List<Long> findIdByDateBetween(LocalDateTime start, LocalDateTime end);

//	@Query(value = "select rm.record_value from data_db inner join records_mapping rm on data_db.id = rm.records_id where (rm.records_id>=3 and rm.records_id<=10) and rm.uni_name='IL2_avg' order by rm.records_id asc", nativeQuery = true);
//	List<Double> findRecordValueByIdBetweenAndUniName();
}

