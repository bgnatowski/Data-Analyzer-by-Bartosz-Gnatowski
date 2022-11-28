package agh.inzapp.inzynierka.database.repositories;

import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface HarmoRepository extends JpaRepository<HarmoDb, Long> {
	List<HarmoDb> findAllByDateBetweenAndTimeBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}