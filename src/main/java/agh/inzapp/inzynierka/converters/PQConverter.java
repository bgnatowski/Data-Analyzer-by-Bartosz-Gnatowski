package agh.inzapp.inzynierka.converters;

import agh.inzapp.inzynierka.database.DataDb;
import agh.inzapp.inzynierka.enums.UniNames;
import agh.inzapp.inzynierka.models.modelObj.PQDataObj;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Transactional
public class PQConverter {
	public static DataDb convertToDb(PQDataObj objModel) {
		DataDb dataDb = new DataDb();
		dataDb.setId(objModel.getId());
		dataDb.setDate(objModel.getLocalDateTime().toLocalDate());
		dataDb.setTime(objModel.getLocalDateTime().toLocalTime());
		dataDb.setRecords(convertRecordsMapToDb(objModel.getRecords()));
		dataDb.setFlags(convertFlagsMapToDb(objModel.getFlags()));

		return dataDb;
	}

	public static PQDataObj convertToObj(DataDb dbModel) {
		PQDataObj pqDataObj = new PQDataObj();
		pqDataObj.setId(dbModel.getId());
		pqDataObj.setColumnsNamesIndexMap(dbModel.getColumnNamesIndexMap());
		pqDataObj.setRecords(convertRecordsMapToObj(dbModel.getRecords()));
		pqDataObj.setFlags(convertFlagsMapToObj(dbModel.getFlags()));
		LocalDateTime dateTime = LocalDateTime.of(dbModel.getDate(), dbModel.getTime());
		pqDataObj.setLocalDateTime(dateTime);

		return pqDataObj;
	}

//	private static Map<UniNames, Double> convertRecordsMapToObj(DataDb dataDb) {
//		Map<UniNames, Double>  map = new TreeMap<>();
//		final Map<String, DataRecord> records = dataDb.getRecords();
//		records.forEach((name, record) -> {
//			map.put(UniNames.valueOf(name), record.getRecord());
//		});
//		return map;
//	}
//
//	public static Map<String, DataRecord> convertRecordsMapToDb(PQDataObj dataObj) {
//		Map<String, DataRecord> map = new TreeMap<>();
//		dataObj.getRecords().forEach((name, record) -> {
//			DataRecord dataRecord = new DataRecord();
//			dataRecord.setId(dataObj.getId());
//			dataRecord.setRecord(dataObj.getRecords().get(name));
//			map.put(name.toString(), dataRecord);
//		});
//		return map;
//	}


	private static Map<UniNames, Double> convertRecordsMapToObj(Map<String, Double> records) {
		Map<UniNames, Double>  map = new TreeMap<>();
		records.forEach((name, record) -> map.put(UniNames.valueOf(name), record));
		return map;
	}

	public static Map<String, Double> convertRecordsMapToDb(Map<UniNames, Double> records) {
		Map<String, Double> map = new TreeMap<>();
		records.forEach((name, record) -> map.put(name.toString(), record));
		return map;
	}

	private static Map<UniNames, String> convertFlagsMapToObj(Map<String, String> flags) {
		Map<UniNames, String> map = new TreeMap<>();
		flags.forEach((name, flag) -> map.put(UniNames.valueOf(name), flag)) ;
		return map;
	}

	private static Map<String, String > convertFlagsMapToDb(Map<UniNames, String> flags) {
		Map<String, String> map = new TreeMap<>();
		flags.forEach((name, flag) -> map.put(name.toString(), flag)) ;
		return map;
	}

	public static List<PQDataObj> convertListDbToObj(List<DataDb> models) {
		ArrayList<PQDataObj> list = new ArrayList<>();
		models.forEach(modelDb -> list.add(convertToObj(modelDb)));
		return list;
	}

	public static List<DataDb> convertListObjToDb(List<PQDataObj> models) {
		ArrayList<DataDb> list = new ArrayList<>();
		models.forEach(modelObj -> list.add(convertToDb(modelObj)));
		return list;
	}

}
