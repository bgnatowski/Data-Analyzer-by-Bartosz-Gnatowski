package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.repositories.DataRepository;
import agh.inzapp.inzynierka.database.models.RecordsMapping;
import agh.inzapp.inzynierka.database.repositories.RecordsMappingRepository;
import agh.inzapp.inzynierka.enums.UniNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class NormalServiceImpl implements CrudService {
	private final DataRepository repository;
	private final RecordsMappingRepository recordsRepository;
	@Autowired
	public NormalServiceImpl(DataRepository repository, RecordsMappingRepository recordsRepository) {
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
