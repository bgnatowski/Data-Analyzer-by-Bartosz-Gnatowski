package agh.inzapp.inzynierka.database.services;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public interface CrudService {

	<T extends CommonDbModel> T add(T dataModel) throws ApplicationException;
	List<? extends CommonDbModel> getAll();
	List<CommonDbModel> findRecordsByIdBetween(Long startId, Long endId);
	List<Timestamp> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
	List<Long> findIdByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

	void clearAll();

	void reset();
}
