package agh.inzapp.inzynierka.database.services;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.repositories.DataRepository;
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
public class NormalServiceImpl implements CrudService {
	@PersistenceContext
	private EntityManager entityManager;

	private final DataRepository repository;
	@Autowired
	public NormalServiceImpl(DataRepository repository) {
		this.repository = repository;
	}
	@Override
	public <T extends CommonDbModel> T add(T dataModel) throws ApplicationException {
		if (dataModel instanceof DataDb)
			return (T) repository.save((DataDb) dataModel);
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
					repository.saveAll((List<DataDb>)dataModelsBatch);
					break;
				}
				List<? extends CommonDbModel> dataModelsBatch = dataModelList.subList(i, i + batchSize);
				repository.saveAll((List<DataDb>)dataModelsBatch);
			}
		}else{
			throw new ApplicationException("error.saving.datadb");
		}
	}


	@Override
	public List<DataDb> getAll(){
		return repository.findAll();
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
		repository.flush();
//		entityManager.createNativeQuery("SELECT setval('data_db_sequence', 1, true);").executeUpdate();
//		entityManager.createNativeQuery("ALTER SEQUENCE data_db_sequence RESTART WITH 1;").executeUpdate();
//		entityManager.createNativeQuery("UPDATE data_db SET id=nextval('data_db_sequence') WHERE id!=null OR id>0;").executeUpdate();
	}


	public void reset() {
	}
}
