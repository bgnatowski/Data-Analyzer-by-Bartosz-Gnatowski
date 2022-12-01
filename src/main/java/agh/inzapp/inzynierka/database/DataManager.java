package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import agh.inzapp.inzynierka.services.HarmonicsServiceImpl;
import agh.inzapp.inzynierka.services.NormalServiceImpl;
import agh.inzapp.inzynierka.utils.converters.DataConverter;
import agh.inzapp.inzynierka.utils.converters.HarmoConverter;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

	public static void saveAll(List<? extends CommonModelFx> modelFxList) throws ApplicationException{
		for (CommonModelFx commonModelFx : modelFxList) {
			save(commonModelFx);
		}
		firstData = true;
		firstHarmo = true;
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
		final List<Timestamp> byDateBetween = dataService.findByDateBetween(startDate, endDate);
		byDateBetween.addAll(harmonicsService.findByDateBetween(startDate, endDate));
		final List<Timestamp> collect = byDateBetween.stream().distinct().collect(Collectors.toList());

		return collect.stream().map(timestamp ->
				timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
				.collect(Collectors.toList());
	}

	public static List<Long> findIdByDateBetween(LocalDateTime startDate, LocalDateTime endDate){
		final List<Long> idByDateBetween = dataService.findIdByDateBetween(startDate, endDate);
		idByDateBetween.addAll(harmonicsService.findIdByDateBetween(startDate,endDate));
		return idByDateBetween.stream().distinct().collect(Collectors.toList());
	}

	public static List<CommonDbModel> findAllByIdBetween(Long idStart, Long idEnd){
		final List<CommonDbModel> recordsByIdBetween = dataService.findRecordsByIdBetween(idStart, idEnd);
		recordsByIdBetween.addAll(harmonicsService.findRecordsByIdBetween(idStart,idEnd));
		return recordsByIdBetween.stream().distinct().collect(Collectors.toList());
	}

}
