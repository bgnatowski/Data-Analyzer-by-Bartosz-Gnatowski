package agh.inzapp.inzynierka.database.repositories;

import agh.inzapp.inzynierka.database.models.DataDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DataDb, Long> {
	List<DataDb> findAllByDateBetweenAndTimeBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
	List<DataDb> findAllByDateAfterAndTimeAfterAndDateBeforeAndTimeBefore(LocalDate date, LocalTime time, LocalDate date2, LocalTime time2);
}
