package agh.inzapp.inzynierka.managers;

import agh.inzapp.inzynierka.entities.BaseDataDb;
import agh.inzapp.inzynierka.entities.PQDataDb;
import agh.inzapp.inzynierka.models.modelObj.BaseDataObj;
import agh.inzapp.inzynierka.models.modelObj.PQDataObj;
import agh.inzapp.inzynierka.service.PQServiceImpl;
import agh.inzapp.inzynierka.converters.PQConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PQDataManager {
	private static PQServiceImpl pqService;

	@Autowired
	public PQDataManager(PQServiceImpl pqService) {
		this.pqService = pqService;
	}

	public static void saveAllModelsInDB(List<? extends BaseDataObj> modelsObjList) {
		modelsObjList.forEach(modelObj -> {
			PQDataDb modelDb = PQConverter.convertToDb((PQDataObj) modelObj);
			pqService.add(modelDb);
		});
	}

	public static void saveModelInDB(PQDataObj modelObj) {
		PQDataDb modelDb = PQConverter.convertToDb((PQDataObj) modelObj);
		pqService.add(modelDb);
	}

//	public static List<? extends BaseDataDb> getAll(){
//		return pqService.getAll();
//	}
}
