package agh.inzapp.inzynierka.database.repositories;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface HarmoRepository extends JpaRepository<HarmoDb, Long> {
	List<CommonDbModel> findAllByDateAfterAndDateBefore(LocalDateTime start, LocalDateTime end);

	@Query(value = "select Date from harmo_db record WHERE record.date BETWEEN ?1 AND ?2", nativeQuery = true)
	List<Timestamp> findByDateBetween(LocalDateTime start, LocalDateTime end);

	@Query(value = "select id from harmo_db record WHERE record.date BETWEEN ?1 AND ?2", nativeQuery = true)
	List<Long> findIdByDateBetween(LocalDateTime start, LocalDateTime end);

	@Query("select h from HarmoDb h where h.id between ?1 and ?2")
	List<CommonDbModel> findAllByIdBetween(Long id, Long id2);
}
