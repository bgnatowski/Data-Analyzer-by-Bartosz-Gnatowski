package agh.inzapp.inzynierka.utils;

import agh.inzapp.inzynierka.exceptions.ApplicationException;
import agh.inzapp.inzynierka.models.CommonModel;
import agh.inzapp.inzynierka.models.FilesList;
import agh.inzapp.inzynierka.strategies.CSVImportPQ;
import agh.inzapp.inzynierka.strategies.CSVImportPQHarmonics;
import agh.inzapp.inzynierka.strategies.CSVStrategy;

import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

	public static List<? extends CommonModel> getNormalDataList(CSVStrategy csvStrategy) {
		List<CommonModel> modelList = new ArrayList<>();
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

	public static List<? extends CommonModel> getHarmonicDataList(CSVStrategy csvStrategy) {
		List<CommonModel> modelList = new ArrayList<>();
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
