package agh.inzapp.inzynierka.service;

import agh.inzapp.inzynierka.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class CrudService implements SavingService {
	private final DataRepository repository;
	private final RecordsMappingRepository recordsRepository;
	@Autowired
	public CrudService(DataRepository repository, RecordsMappingRepository recordsRepository) {
		this.repository = repository;
		this.recordsRepository = recordsRepository;
	}
	@Override
	public <T> T add(T dataModel) {
		DataDb persisted = repository.save((DataDb) dataModel);
		return (T) persisted;
	}
	@Override
	public List<DataDb> getAll(){
		final List<DataDb> all = repository.findAll();
		final List<RecordsMapping> records = recordsRepository.findAll();

		all.forEach(dataDb -> {
			Map<String, Double> recordsMap = new LinkedHashMap<>();
			final List<RecordsMapping> collect = records.stream().filter(record -> record.getId().getRecordsId().equals(dataDb.getId()))
					.collect(Collectors.toList());
			collect.forEach(record -> recordsMap.put(record.getId().getUniName(), record.getValue()));
			dataDb.setRecords(recordsMap);
//			final String uniName = recordsMapping.getId().getUniName();
//			final Double value = recordsMapping.getValue();
//			recordsMap.put(uniName, value);
		});
		return all;
	}



}
