package agh.inzapp.inzynierka.utils.converters;

import agh.inzapp.inzynierka.database.dbmodels.PQRecords;
import agh.inzapp.inzynierka.database.dbmodels.PQDataDb;
import agh.inzapp.inzynierka.models.modelObj.PQDataObj;
import agh.inzapp.inzynierka.utils.enums.UnitaryNames;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PQConverter {
	public static PQDataDb convertToDb(PQDataObj objModel){
		PQDataDb pqDataDb = new PQDataDb();

		pqDataDb.setDate(objModel.getLocalDateTime().toLocalDate());
		pqDataDb.setTime(objModel.getLocalDateTime().toLocalTime());
		pqDataDb.setRecords(convertRecordsMapToDb(objModel.getRecords()));
		pqDataDb.setFlag(objModel.getFlag());

		return pqDataDb;
	}
	public static PQDataObj convertToObj(PQDataDb dbModel){
		PQDataObj PQDataObj = new PQDataObj();

		PQDataObj.setColumnsNamesIndexMap(dbModel.getColumnNamesIndexMap());
		PQDataObj.setRecords(convertRecordsMapToObj(dbModel.getRecords()));
		PQDataObj.setFlags(dbModel.getFlag());
		LocalDateTime dateTime = LocalDateTime.of(dbModel.getDate(), dbModel.getTime());
		PQDataObj.setLocalDateTime(dateTime);

		return PQDataObj;
	}

	private static Map<UnitaryNames, Double> convertRecordsMapToObj(Map<UnitaryNames, PQRecords> records) {
		Map<UnitaryNames, Double> map = new TreeMap<>();
		records.forEach((name, record) -> {
			map.put(name, record.getRecord());
		});
		return map;
	}

	public static Map<UnitaryNames, PQRecords> convertRecordsMapToDb(Map<UnitaryNames, Double> recordsMap) {
		Map<UnitaryNames, PQRecords> map = new TreeMap<>();
		recordsMap.forEach((name, record) -> {
			map.put(name, new PQRecords(name, record));
		});
		return map;
	}

	public static List<PQDataObj> convertListDbToObj(List<PQDataDb> models) {
		ArrayList<PQDataObj> list = new ArrayList<>();
		models.forEach(modelDb -> {
			list.add(convertToObj(modelDb));
		});
		return list;
	}

	public static List<PQDataDb> convertListObjToDb(List<PQDataObj> models) {
		ArrayList<PQDataDb> list = new ArrayList<>();
		models.forEach(modelObj -> {
			list.add(convertToDb(modelObj));
		});
		return list;
	}

}
