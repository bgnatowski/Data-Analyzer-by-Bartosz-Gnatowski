package agh.inzapp.inzynierka.database.services;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.database.repositories.HarmoRepository;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class HarmonicsServiceImpl implements CrudService{
	@PersistenceContext
	private EntityManager entityManager;

	private final HarmoRepository repository;
	@Autowired
	public HarmonicsServiceImpl(HarmoRepository repository) {
		this.repository = repository;
	}
	@Override
	public <T extends CommonDbModel> T add(T dataModel) throws ApplicationException {
		if (dataModel instanceof HarmoDb)
			return (T) repository.save((HarmoDb) dataModel);
		else
			throw new ApplicationException("error.saving.datadb");
	}

	@Override
	public void addAll(List<? extends CommonDbModel> dataModelList) throws ApplicationException {
		if(dataModelList != null){
			int totalObjects = dataModelList.size();
			int batchSize = 1000;
			for (int i = 0; i < totalObjects; i = i + batchSize) {
				if( i+ batchSize > totalObjects){
					List<? extends CommonDbModel> dataModelsBatch = dataModelList.subList(i, totalObjects - 1);
					repository.saveAll((List<HarmoDb>) dataModelsBatch);
					break;
				}
				List<? extends CommonDbModel> dataModelsBatch = dataModelList.subList(i, i + batchSize);
				repository.saveAll((List<HarmoDb>) dataModelsBatch);
			}
		}
		else{
			throw new ApplicationException("error.saving.datadb");
		}
	}

	@Override
	public List<? extends CommonDbModel> getAll() {
		return repository.findAll();
	}
	@Override
	public void clearAll() {
		repository.deleteAll();
		repository.flush();
//		entityManager.createNativeQuery("ALTER SEQUENCE harmo_db_sequence RESTART WITH 1;\n" +
//				"UPDATE harmo_db SET id=nextval('harmo_db_sequence');").executeUpdate();
//		entityManager.createNativeQuery("SELECT setval('harmo_db_sequence', 1, true);").executeUpdate();
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

	public void reset() {
	}
}
