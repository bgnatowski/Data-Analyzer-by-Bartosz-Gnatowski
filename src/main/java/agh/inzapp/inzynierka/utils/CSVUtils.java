package agh.inzapp.inzynierka.utils;

import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.FilesList;
import agh.inzapp.inzynierka.strategies.CSVStrategy;

import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
	public static List<? extends CommonModelFx> importNormalDataList(CSVStrategy csvStrategy) {
		List<CommonModelFx> modelList = new ArrayList<>();
		FilesList.getInstance().getListNormal().forEach(file ->
		{
			try {
				modelList.addAll(csvStrategy.importCSVFile(file.getAbsolutePath()));
			} catch (ApplicationException e) {
				DialogUtils.errorDialog(e.getMessage());
			}
		});
		return modelList;
	}

	public static List<? extends CommonModelFx> importHarmonicsDataList(CSVStrategy csvStrategy) {
		List<CommonModelFx> modelList = new ArrayList<>();
		FilesList.getInstance().getListHarmonics().forEach(file ->
		{
			try {
				modelList.addAll(csvStrategy.importCSVFile(file.getAbsolutePath()));
			} catch (ApplicationException e) {
				DialogUtils.errorDialog(e.getMessage());
			}
		});
		return modelList;
	}
}
