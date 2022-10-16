package agh.inzapp.inzynierka.service;

import agh.inzapp.inzynierka.database.DataDb;
import agh.inzapp.inzynierka.database.DataRepository;
import agh.inzapp.inzynierka.database.RecordsMapping;
import agh.inzapp.inzynierka.database.RecordsMappingRepository;
import agh.inzapp.inzynierka.enums.UniNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrudServiceImpl implements CrudService {
	private final DataRepository repository;
	private final RecordsMappingRepository recordsRepository;
	@Autowired
	public CrudServiceImpl(DataRepository repository, RecordsMappingRepository recordsRepository) {
		this.repository = repository;
		this.recordsRepository = recordsRepository;
	}
	@Override
	public DataDb add(DataDb dataModel) {
		return repository.save(dataModel);
	}
	@Override
	public List<DataDb> getAll(){
		final List<DataDb> all = repository.findAll();
		final List<RecordsMapping> records = recordsRepository.findAll();

		all.forEach(dataDb -> {
			Map<UniNames, Double> recordsMap = new LinkedHashMap<>();
			final List<RecordsMapping> collect = records.stream()
					.filter(record -> record.getId().getRecordsId().equals(dataDb.getId()))
					.toList();
			collect.forEach(record -> recordsMap.put(record.getId().getUniName(), record.getRecordValue()));
			dataDb.setRecords(recordsMap);
		});
		return all;
	}



}
