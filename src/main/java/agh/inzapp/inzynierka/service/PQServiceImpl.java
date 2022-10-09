package agh.inzapp.inzynierka.service;

import agh.inzapp.inzynierka.entities.BaseDataDb;
import agh.inzapp.inzynierka.entities.PQDataDb;
import agh.inzapp.inzynierka.repositories.PQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PQServiceImpl implements SavingInDBService {
	private final PQRepository pqRepository;

	@Autowired
	public PQServiceImpl(PQRepository pqRepository) {
		this.pqRepository = pqRepository;
	}

	@Override
	public <T extends BaseDataDb> T add(T dataModel) {
		PQDataDb persisted = pqRepository.save((PQDataDb) dataModel);
		return (T) persisted;
	}

	@Override
	public List<? extends BaseDataDb> getAll(){
		return pqRepository.findAll();
	}

}
