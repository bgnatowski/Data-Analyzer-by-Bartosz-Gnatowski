package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.converters.DataConverter;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.models.DataFx;
import agh.inzapp.inzynierka.models.PQHarmonicsFx;
import agh.inzapp.inzynierka.models.PQNormalFx;
import agh.inzapp.inzynierka.services.HarmonicsServiceImpl;
import agh.inzapp.inzynierka.services.NormalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataManager {
	private static NormalServiceImpl dataService;
	private static HarmonicsServiceImpl harmonicsService;
	@Autowired
	public DataManager(NormalServiceImpl service, HarmonicsServiceImpl harmonicsService) {
		dataService = service;
		harmonicsService = harmonicsService;
	}

	public static void saveAll(List<? extends DataFx> dataFxes) {
		dataFxes.forEach(dataFx -> {

			DataDb modelDb;

			if (dataFx instanceof PQNormalFx) {
//				System.out.println("PQNORMALFX");
				modelDb = DataConverter.convertFxToDb(dataFx);
				dataService.add(modelDb);
			} else if (dataFx instanceof PQHarmonicsFx){
//				System.out.println("PQHARMONICSFX");
//				modelDb = HarmoConverter.convertFxToDb(dataFx);
//				harmonicsService.add(modelDb);
			}
			//todo crudService.add(sonel model)
//			if (dataObj instanceof SonelDataObj) {
//				modelDb = SonelConverter.convertToDb((SonelDataObj) dataObj);
//			}

		});
	}

	public static <T extends DataFx> void save(T dataFx) {
		DataDb modelDb;
		if (dataFx instanceof DataFx){
			modelDb = DataConverter.convertFxToDb(dataFx);
			dataService.add(modelDb);
		}
//		if (dataObj instanceof SonelDataObj)
//			modelDb = SonelConverter.convertToDb((SonelDataObj) dataObj);

	}

	public static List<DataDb> getAll() {
		return dataService.getAll();
	}

}
