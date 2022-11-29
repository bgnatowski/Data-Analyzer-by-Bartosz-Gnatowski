package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface CrudService {

	<T extends CommonDbModel> T add(T dataModel) throws ApplicationException;
	List<? extends CommonDbModel> getAll();
	List<? extends CommonDbModel> findAllByDateAfterAndDateBefore(LocalDateTime startDate, LocalDateTime endDate);
	List<Timestamp> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
	List<Long> findIdByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

	void clearAll();


}
