package agh.inzapp.inzynierka.utils.converters;

import agh.inzapp.inzynierka.database.dbmodels.WinPQDataDb;
import agh.inzapp.inzynierka.models.modelObj.WinPQDataObj;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConverterWinPQData {
	public static WinPQDataDb convertToDb(WinPQDataObj objModel){
		WinPQDataDb winPQDataDb = new WinPQDataDb();

		winPQDataDb.setColumnsNames(objModel.getColumnsNames());
		winPQDataDb.setDate(DateConverter.convertToDate(objModel.getLocalDateTime().toLocalDate()));
		winPQDataDb.setTime(DateConverter.convertToTimeAsDate(objModel.getLocalDateTime().toLocalTime()));
		winPQDataDb.setRecords(objModel.getRecords());
		winPQDataDb.setFlag(objModel.getFlag());

		return winPQDataDb;
	}
	public static WinPQDataObj convertToObj(WinPQDataDb dbModel){
		WinPQDataObj winPQDataObj = new WinPQDataObj();

		winPQDataObj.setColumnsNames(dbModel.getColumnsNames());
		winPQDataObj.setRecords(dbModel.getRecords());
		winPQDataObj.setFlags(dbModel.getFlag());
		LocalDateTime dateTime = LocalDateTime.of(DateConverter.convertToLocalDate(dbModel.getDate()), DateConverter.convertToLocalTime(dbModel.getTime()));
		winPQDataObj.setLocalDateTime(dateTime);

		return winPQDataObj;
	}

	public static List<WinPQDataObj> convertListDbToObj(List<WinPQDataDb> models) {
		ArrayList<WinPQDataObj> list = new ArrayList<>();
		models.forEach(modelDb -> {
			list.add(convertToObj(modelDb));
		});
		return list;
	}

	public static List<WinPQDataDb> convertListObjToDb(List<WinPQDataObj> models) {
		ArrayList<WinPQDataDb> list = new ArrayList<>();
		models.forEach(modelObj -> {
			list.add(convertToDb(modelObj));
		});
		return list;
	}

}
