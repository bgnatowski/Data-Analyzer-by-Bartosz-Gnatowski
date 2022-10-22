package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.converters.DataConverter;
import agh.inzapp.inzynierka.models.modelObj.BaseDataObj;
import agh.inzapp.inzynierka.models.modelObj.PQDataObj;
import agh.inzapp.inzynierka.service.CrudService;
import agh.inzapp.inzynierka.service.HarmonicsServiceImpl;
import agh.inzapp.inzynierka.service.NormalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	public static void saveAll(List<? extends BaseDataObj> dataObjList) {
		dataObjList.forEach(dataObj -> {
			DataDb modelDb;
			if (dataObj instanceof PQDataObj) {
				modelDb = DataConverter.convertToDb(dataObj);
				dataService.add(modelDb);
			}
			//todo crudService.add(sonel model)
//			if (dataObj instanceof SonelDataObj) {
//				modelDb = SonelConverter.convertToDb((SonelDataObj) dataObj);
//			}

		});
	}

	public static <T extends BaseDataObj> void save(T dataObj) {
		DataDb modelDb;
		if (dataObj instanceof PQDataObj){
			modelDb = DataConverter.convertToDb(dataObj);
			dataService.add(modelDb);
		}
//		if (dataObj instanceof SonelDataObj)
//			modelDb = SonelConverter.convertToDb((SonelDataObj) dataObj);

	}

	public static List<DataDb> getAll() {
		return dataService.getAll();
	}
}
