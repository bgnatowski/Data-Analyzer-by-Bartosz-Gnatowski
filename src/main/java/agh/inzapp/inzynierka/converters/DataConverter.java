package agh.inzapp.inzynierka.converters;

import agh.inzapp.inzynierka.database.DataDb;
import agh.inzapp.inzynierka.enums.UniNames;
import agh.inzapp.inzynierka.models.modelFx.DataFx;
import agh.inzapp.inzynierka.models.modelObj.BaseDataObj;
import agh.inzapp.inzynierka.models.modelObj.PQDataObj;
import com.sun.javafx.collections.ObservableMapWrapper;
import javafx.collections.FXCollections;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@Transactional
public class DataConverter {
	public static DataDb convertToDb(BaseDataObj dataObj) {
		DataDb dataDb = new DataDb();
		dataDb.setId(dataObj.getId());
		dataDb.setDate(dataObj.getLocalDateTime().toLocalDate());
		dataDb.setTime(dataObj.getLocalDateTime().toLocalTime());
		dataDb.setRecords(dataObj.getRecords());
		dataDb.setFlags(convertFlagsMapToDb(dataObj.getFlags()));
		dataDb.setColumnNames(dataObj.getColumnNames());
		return dataDb;
	}

	public static BaseDataObj convertToObj(DataDb dataDb) {
		BaseDataObj dataObj;

//		if (dataDb.getRecords().containsKey(UniNames.Flag)){
//			dataObj = new PQDataObj();
//		} else {
//			dataObj = new SonelDataObj();
//		}
		dataObj = new PQDataObj();
		dataObj.setId(dataDb.getId());
		LocalDateTime dateTime = LocalDateTime.of(dataDb.getDate(), dataDb.getTime());
		dataObj.setLocalDateTime(dateTime);
		dataObj.setFlags(convertFlagPatternToMap(dataDb.getFlags()));
		dataObj.setRecords(dataDb.getRecords());
		dataObj.setColumnNames(dataDb.getColumnNames());

		return dataObj;
	}


	private static Map<UniNames, String> convertFlagPatternToMap(String flagPattern) {
		Map<UniNames, String> map = new LinkedHashMap<>();

		if (flagPattern.contains("|")){
			final String[] split = flagPattern.split("\\|");
			map.put(UniNames.Flag_E, split[0]);
			map.put(UniNames.Flag_P, split[2]);
			map.put(UniNames.Flag_G, split[4]);
			map.put(UniNames.Flag_T, split[6]);
			map.put(UniNames.Flag_A, split[8]);
		} else {
			map.put(UniNames.Flag, flagPattern);
		}
		map.replaceAll((k, v) -> v.equals("o") ? null : v);
		return map;
	}
	private static String convertFlagsMapToDb(Map<UniNames, String> flags) {
		if (flags.containsKey(UniNames.Flag)) return flags.get(UniNames.Flag);

		StringBuilder sb = new StringBuilder();
		flags.forEach((name, flag) -> sb.append(flag).append("|"));
		return sb.toString();
	}
	public static List<BaseDataObj> convertListDbToObj(List<DataDb> models) {
		ArrayList<BaseDataObj> list = new ArrayList<>();
		models.forEach(modelDb -> list.add(convertToObj(modelDb)));
		return list;
	}
	public static List<DataDb> convertListObjToDb(List<? extends BaseDataObj> models) {
		ArrayList<DataDb> list = new ArrayList<>();
		models.forEach(modelObj -> list.add(convertToDb(modelObj)));
		return list;
	}

	public static DataFx convertDbToFx(DataDb dataDb) {
		DataFx dataFx = new DataFx();
		dataFx.setId(dataDb.getId());
		dataFx.setDate(dataDb.getDate());
		dataFx.setTime(dataDb.getTime());
		dataFx.setRecords(FXCollections.observableMap(convertRecordsMap(dataDb.getRecords())));
		dataFx.setFlags(FXCollections.observableMap(convertFlagPatternToMap(dataDb.getFlags())));
		dataFx.setColumnNames(FXCollections.observableArrayList(dataDb.getColumnNames()));
		return dataFx;
	}

	private static Map<UniNames, Double> convertRecordsMap(Map<UniNames, Double> records) {
		records.replaceAll((k, v) -> v.equals(0.0) ? null : v);
		return records;
	}

	public static DataDb convertFxToDb(DataFx dataFx) {
		DataDb dataDb = new DataDb();
		dataDb.setId(dataFx.getId());
		dataDb.setDate(dataFx.getDate());
		dataDb.setTime(dataFx.getTime());
		dataDb.setRecords(dataFx.getRecords());
		dataDb.setFlags(convertFlagsMapToDb(dataFx.getFlags()));
		dataDb.setColumnNames(dataFx.getColumnNames());

		return dataDb;
	}
}
