package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public interface CrudService {

	<T extends CommonDbModel> T add(T dataModel) throws ApplicationException;
	List<? extends CommonDbModel> getAll();
	List<? extends CommonDbModel> findAllByDateBetweenAndTimeBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

	void clearAll();


}
