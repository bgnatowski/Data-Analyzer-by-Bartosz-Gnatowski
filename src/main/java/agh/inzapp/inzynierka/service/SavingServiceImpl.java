package agh.inzapp.inzynierka.service;

import agh.inzapp.inzynierka.database.DataDb;
import agh.inzapp.inzynierka.database.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class SavingServiceImpl implements SavingService {
	private final DataRepository repository;
	@Autowired
	public SavingServiceImpl(DataRepository repository) {
		this.repository = repository;
	}

	@Override
	public <T> T add(T dataModel) {
		DataDb persisted = repository.save((DataDb) dataModel);
		return (T) persisted;
	}
	@Override

	public List<DataDb> getAll(){
		return repository.findAll();
	}

}
