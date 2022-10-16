package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.converters.DataConverter;
import agh.inzapp.inzynierka.models.modelObj.BaseDataObj;
import agh.inzapp.inzynierka.models.modelObj.PQDataObj;
import agh.inzapp.inzynierka.service.CrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataManager {
	private static CrudServiceImpl crudService;
	@Autowired
	public DataManager(CrudServiceImpl service) {
		crudService = service;
	}
	public static void saveAll(List<? extends BaseDataObj> dataObjList) {
		dataObjList.forEach(dataObj -> {
			DataDb modelDb;
			if (dataObj instanceof PQDataObj) {
				modelDb = DataConverter.convertToDb(dataObj);
				crudService.add(modelDb);
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
			crudService.add(modelDb);
		}
//		if (dataObj instanceof SonelDataObj)
//			modelDb = SonelConverter.convertToDb((SonelDataObj) dataObj);

	}

	public static List<DataDb> getAll() {
		return crudService.getAll();
	}
}
