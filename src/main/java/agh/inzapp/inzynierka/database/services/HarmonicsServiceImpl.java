package agh.inzapp.inzynierka.database.services;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.database.repositories.HarmoRepository;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class HarmonicsServiceImpl implements CrudService{
	private final HarmoRepository repository;
	@Autowired
	public HarmonicsServiceImpl(HarmoRepository repository) {
		this.repository = repository;
	}
	@Override
	public <T extends CommonDbModel> T add(T dataModel) throws ApplicationException {
		HarmoDb saved;
		if (dataModel instanceof HarmoDb){
			saved = repository.save((HarmoDb) dataModel);
			return (T) saved;
		} else {
			throw new ApplicationException("error.saving.datadb");
		}
	}

	@Override
	public List<? extends CommonDbModel> getAll() {
		List<HarmoDb> all = repository.findAll();
		return all;
	}
	@Override
	public void clearAll() {
		repository.deleteAll();
	}

	@Override
	@Transactional
	public void reset() {
		repository.reset();
	}

	@Override
	public List<CommonDbModel> findRecordsByIdBetween(Long startId, Long endId){
		return repository.findAllByIdBetween(startId, endId);
	}
	@Override
	public List<Timestamp> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate){
		return repository.findByDateBetween(startDate, endDate);
	}

	@Override
	public List<Long> findIdByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return repository.findIdByDateBetween(startDate, endDate);
	}
}
