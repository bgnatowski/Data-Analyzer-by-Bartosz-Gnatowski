package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.repositories.DataRepository;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NormalServiceImpl implements CrudService {
	private final DataRepository repository;
	@Autowired
	public NormalServiceImpl(DataRepository repository) {
		this.repository = repository;
	}
	@Override
	public <T extends CommonDbModel> T add(T dataModel) throws ApplicationException {
		DataDb saved;
		if (dataModel instanceof DataDb){
			 saved = repository.save((DataDb) dataModel);
			 return (T) saved;
		} else {
			throw new ApplicationException("error.saving.datadb");
		}
	}

	@Override
	public List<DataDb> getAll(){
		final List<DataDb> all = repository.findAll();
		return all;
	}

	@Override
	public List<CommonDbModel> findRecordsByIdBetween(Long startId, Long endId) {
		return repository.findAllByIdBetween(startId, endId);
	}

	@Override
	public List<Timestamp> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate){
		return repository.findByDateBetween(startDate, endDate);
	}
	public List<Long> findIdByDateBetween(LocalDateTime startDate, LocalDateTime endDate){
		return repository.findIdByDateBetween(startDate, endDate);
	}

	@Override
	public void clearAll() {
		repository.deleteAll();
	}

}
