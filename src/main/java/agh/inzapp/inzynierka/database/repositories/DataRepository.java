package agh.inzapp.inzynierka.database.repositories;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DataDb, Long> {
	List<CommonDbModel> findAllByDateAfterAndDateBefore(LocalDateTime date, LocalDateTime date2);
	@Query(value = "select Date from data_db record WHERE record.date BETWEEN ?1 AND ?2", nativeQuery = true)
	List<Timestamp> findByDateBetween(LocalDateTime start, LocalDateTime end);
	@Query(value = "select id from data_db record WHERE record.date BETWEEN ?1 AND ?2", nativeQuery = true)
	List<Long> findIdByDateBetween(LocalDateTime start, LocalDateTime end);
	@Query("select d from DataDb d where d.id between ?1 and ?2")
	List<CommonDbModel> findAllByIdBetween(Long idStart, Long idEnd);

//	@Query(value = "select rm.record_value from data_db inner join records_mapping rm on data_db.id = rm.records_id where ((data_db.id>=?1 and data_db.id<=?2) and rm.uni_name=?3)", nativeQuery = true)
//	List<Double> findALlRecordValueByDataDbIdBetweenAndRecordsContainingUniName(Long idStart, Long idEnd, UniNames uniName);


}

