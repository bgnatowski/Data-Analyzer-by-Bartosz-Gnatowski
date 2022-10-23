package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.converters.DataConverter;
import agh.inzapp.inzynierka.converters.HarmoConverter;
import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.exceptions.ApplicationException;
import agh.inzapp.inzynierka.models.CommonModel;
import agh.inzapp.inzynierka.models.DataFx;
import agh.inzapp.inzynierka.models.HarmoFx;
import agh.inzapp.inzynierka.services.HarmonicsServiceImpl;
import agh.inzapp.inzynierka.services.NormalServiceImpl;
import agh.inzapp.inzynierka.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	public static void saveAll(List<? extends CommonModel> modelFxList) {
		modelFxList.forEach(modelFx -> {
			try {
				save(modelFx);
			} catch (ApplicationException e) {
				DialogUtils.errorDialog(e.getMessage());
			}
		});
	}

	public static <T extends CommonModel> void save(T modelFx) throws ApplicationException {
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

}
