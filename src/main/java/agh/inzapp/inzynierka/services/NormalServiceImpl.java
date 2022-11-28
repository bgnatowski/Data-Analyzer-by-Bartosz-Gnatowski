package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.repositories.DataRepository;
import agh.inzapp.inzynierka.database.mappings.RecordsMapping;
import agh.inzapp.inzynierka.database.repositories.RecordsMappingRepository;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	public List<? extends CommonDbModel> findAllByDateBetweenAndTimeBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
//		return repository.findAllByDateBetweenAndTimeBetween(startDate, endDate, startTime, endTime);
		return repository.findAllByDateAfterAndTimeAfterAndDateBeforeAndTimeBefore(startDate, startTime, endDate, endTime);
	}

	@Override
	public void clearAll() {
		repository.deleteAll();
	}

}
