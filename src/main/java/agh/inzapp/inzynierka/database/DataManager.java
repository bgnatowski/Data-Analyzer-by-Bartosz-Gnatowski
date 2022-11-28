package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.models.HarmoDb;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class DataManager {
	private static NormalServiceImpl dataService;
	private static HarmonicsServiceImpl harmonicsService;

	@Autowired
	public DataManager(NormalServiceImpl dataService, HarmonicsServiceImpl harmonicsService) {
		this.dataService = dataService;
		this.harmonicsService = harmonicsService;
	}

	public static void saveAll(List<? extends CommonModelFx> modelFxList) throws ApplicationException{
		for (CommonModelFx commonModelFx : modelFxList) {
			save(commonModelFx);
		}
	}

	public static <T extends CommonModelFx> void save(T modelFx) throws ApplicationException {
		CommonDbModel modelDb;
		if (modelFx instanceof DataFx) {
			modelDb = DataConverter.convertFxToDb((DataFx) modelFx);
			dataService.add(modelDb);
		}
		if (modelFx instanceof HarmoFx) {
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

	public static List<? extends CommonDbModel> findAllNormalByDateBetweenAndTimeBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
		return dataService.findAllByDateBetweenAndTimeBetween(startDate, endDate, startTime, endTime);
	}
	public static List<? extends CommonDbModel> findAllHarmoByDateBetweenAndTimeBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
		return harmonicsService.findAllByDateBetweenAndTimeBetween(startDate, endDate, startTime, endTime);
	}

}
