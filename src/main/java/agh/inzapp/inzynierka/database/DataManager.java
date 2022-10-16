package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.converters.PQConverter;
import agh.inzapp.inzynierka.models.modelObj.BaseDataObj;
import agh.inzapp.inzynierka.models.modelObj.PQDataObj;
import agh.inzapp.inzynierka.models.modelObj.SonelDataObj;
import agh.inzapp.inzynierka.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataManager {
	private static CrudService crudService;
	@Autowired
	public DataManager(CrudService service) {
		this.crudService = service;
	}
	public static void saveAll(List<? extends BaseDataObj> dataObjList) {
		dataObjList.forEach(dataObj -> {
			DataDb modelDb;
			if (dataObj instanceof PQDataObj) {
				modelDb = PQConverter.convertToDb((PQDataObj) dataObj);
				crudService.add(modelDb);
			}
			if (dataObj instanceof SonelDataObj) {
//				modelDb = SonelConverter.convertToDb((SonelDataObj) dataObj);
			}

		});
	}

	public static <T extends BaseDataObj> void save(T dataObj) {
		DataDb modelDb = new DataDb();
		if (dataObj instanceof PQDataObj)
			modelDb = PQConverter.convertToDb((PQDataObj) dataObj);
		if (dataObj instanceof SonelDataObj)
//			modelDb = SonelConverter.convertToDb((SonelDataObj) dataObj);
			crudService.add(modelDb);
	}

	public static List<DataDb> getAll() {
		return crudService.getAll();
	}
}
