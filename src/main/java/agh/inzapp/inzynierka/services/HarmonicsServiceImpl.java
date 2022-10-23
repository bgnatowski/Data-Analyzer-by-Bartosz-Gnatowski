package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.database.mappings.HarmonicsMapping;
import agh.inzapp.inzynierka.database.mappings.ThdMapping;
import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.database.repositories.*;
import agh.inzapp.inzynierka.enums.UniNames;
import agh.inzapp.inzynierka.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class HarmonicsServiceImpl implements CrudService{
	private final HarmoRepository repository;
	private final HarmonicsMappingRepository harmonicsMappingRepository;
	private final ThdMappingRepository thdMappingRepository;
	@Autowired
	public HarmonicsServiceImpl(HarmoRepository repository, HarmonicsMappingRepository harmonicsMappingRepository, ThdMappingRepository thdMappingRepository) {
		this.repository = repository;
		this.harmonicsMappingRepository = harmonicsMappingRepository;
		this.thdMappingRepository = thdMappingRepository;
	}
	@Override
	public <T extends CommonDbModel> T add(T dataModel) throws ApplicationException {
		HarmoDb saved;
		if (dataModel instanceof DataDb){
			saved = repository.save((HarmoDb) dataModel);
			return (T) saved;
		} else {
			throw new ApplicationException("error.saving.datadb");
		}
	}

	@Override
	public List<? extends CommonDbModel> getAll() {
		List<HarmoDb> all = repository.findAll();
		final List<HarmonicsMapping> harmonicsMapping = harmonicsMappingRepository.findAll();
		final List<ThdMapping> thdMapping = thdMappingRepository.findAll();

		all.forEach(dataDb -> {
			Map<UniNames, Double> harmonicsMap = new LinkedHashMap<>();
			Map<UniNames, Double> thdMap = new LinkedHashMap<>();
			final List<HarmonicsMapping> collectHarmonics = harmonicsMapping.stream()
					.filter(record -> record.getId().getHarmonicsId().equals(dataDb.getId()))
					.toList();
			final List<ThdMapping> collectThd = thdMapping.stream()
					.filter(record -> record.getId().getThdId().equals(dataDb.getId()))
					.toList();
			collectHarmonics.forEach(record -> harmonicsMap.put(record.getId().getUniName(), record.getHarmonicValue()));
			collectThd.forEach(record -> thdMap.put(record.getId().getUniName(), record.getThdValue()));

			dataDb.setHarmonics(harmonicsMap);
			dataDb.setThd(thdMap);
		});

		return all;
	}
}
