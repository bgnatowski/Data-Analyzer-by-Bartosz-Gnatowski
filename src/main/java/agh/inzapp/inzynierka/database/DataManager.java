package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import agh.inzapp.inzynierka.database.services.HarmonicsServiceImpl;
import agh.inzapp.inzynierka.database.services.NormalServiceImpl;
import agh.inzapp.inzynierka.models.fxmodels.ListHarmoFx;
import agh.inzapp.inzynierka.utils.converters.DataConverter;
import agh.inzapp.inzynierka.utils.converters.HarmoConverter;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataManager {
	private static NormalServiceImpl dataService;
	private static HarmonicsServiceImpl harmonicsService;
	private static boolean firstData = true;
	private static boolean firstHarmo = true;
	@Autowired
	public DataManager(NormalServiceImpl dataService, HarmonicsServiceImpl harmonicsService) {
		this.dataService = dataService;
		this.harmonicsService = harmonicsService;
	}

	public static void saveAll(List<? extends CommonDbModel> dbModelsList) throws ApplicationException{
		if(!dbModelsList.isEmpty() && dbModelsList.get(0) instanceof DataDb)
			dataService.addAll(dbModelsList);
		else if(!dbModelsList.isEmpty() && dbModelsList.get(0) instanceof HarmoDb)
			harmonicsService.addAll(dbModelsList);
	}

	public static <T extends CommonModelFx> void save(T modelFx) throws ApplicationException {
		CommonDbModel modelDb;

		if (modelFx instanceof DataFx) {
			if(firstData) {
				dataService.clearAll();
				firstData=false;
			}
			modelDb = DataConverter.convertFxToDb((DataFx) modelFx);
			dataService.add(modelDb);
		}
		if (modelFx instanceof HarmoFx) {
			if(firstHarmo){
				harmonicsService.clearAll();
				firstHarmo = false;
			}
			modelDb = HarmoConverter.convertFxToDb((HarmoFx) modelFx);
			harmonicsService.add(modelDb);
		}
	}

	public static <T extends CommonDbModel> List<? extends CommonDbModel> getAll(Class<T> clazz) {
		if (clazz.equals(DataDb.class))
			return dataService.getAll();
		else if(clazz.equals(HarmoDb.class))
			return harmonicsService.getAll();
		return null;
	}

	public static void clearNormal(){
		dataService.clearAll();
	}
	public static void clearHarmo(){
		harmonicsService.clearAll();
	}
	public static List<LocalDateTime> findTimeSeriesByLocalDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate){
		List<Timestamp> byDateBetween = new ArrayList<>();
		byDateBetween.addAll(dataService.findByDateBetween(startDate, endDate));
		byDateBetween.addAll(harmonicsService.findByDateBetween(startDate, endDate));
		List<Timestamp> listWithoutDuplicates = byDateBetween.stream().distinct().collect(Collectors.toList());

		return listWithoutDuplicates.stream().map(timestamp ->
				timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
				.collect(Collectors.toList());
	}

	public static List<Long> findIdByDateBetween(LocalDateTime startDate, LocalDateTime endDate){
		List<Long> idByDateBetween = new ArrayList<>();
		idByDateBetween.addAll(dataService.findIdByDateBetween(startDate,endDate));
		idByDateBetween.addAll(harmonicsService.findIdByDateBetween(startDate,endDate));
		return idByDateBetween.stream().distinct().collect(Collectors.toList());
	}

	public static List<CommonDbModel> findAllByIdBetween(Long idStart, Long idEnd){
		List<CommonDbModel> list = new ArrayList<>();
		list.addAll(dataService.findRecordsByIdBetween(idStart,idEnd));
		list.addAll(harmonicsService.findRecordsByIdBetween(idStart,idEnd));
		return list;
	}


	public static void resetTables(){
		dataService.reset();
		harmonicsService.reset();
	}
}
