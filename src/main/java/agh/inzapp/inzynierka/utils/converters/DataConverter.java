package agh.inzapp.inzynierka.utils.converters;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import javafx.collections.FXCollections;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Transactional
public class DataConverter {
	public static DataFx convertDbToFx(DataDb dataDb) {
		DataFx dataFx = new DataFx();
		dataFx.setId(dataDb.getId());
		dataFx.setDate(dataDb.getDate());
		dataFx.setFlags(FXCollections.observableMap(convertFlagPatternToMap(dataDb.getFlags())));
		dataFx.setColumnNames(FXCollections.observableArrayList(dataDb.getColumnNames()));
		dataFx.setRecords(FXCollections.observableMap(convertRecordsMap(dataDb.getRecords())));

		return dataFx;
	}
	public static DataDb convertFxToDb(DataFx dataFx) {
		DataDb dataDb = new DataDb();
		dataDb.setId(dataFx.getId());
		dataDb.setDate(dataFx.getDate());
		dataDb.setFlags(convertFlagsMapToDb(dataFx.getFlags()));
		dataDb.setColumnNames(dataFx.getColumnNames());
		dataDb.setRecords(dataFx.getRecords());
		return dataDb;
	}

	static Map<UniNames, Double> convertRecordsMap(Map<UniNames, Double> records) {
		records.replaceAll((k, v) -> v.equals(0.0) ? null : v);
		return records;
	}

	static String convertFlagsMapToDb(Map<UniNames, String> flags) {
		if (flags.containsKey(UniNames.Flag)) return flags.get(UniNames.Flag);

		StringBuilder sb = new StringBuilder();
		flags.forEach((name, flag) -> sb.append(flag).append("|"));
		return sb.toString();
	}

	static Map<UniNames, String> convertFlagPatternToMap(String flagPattern) {
		Map<UniNames, String> map = new LinkedHashMap<>();

		if (flagPattern.contains("|")){
			final String[] split = flagPattern.split("\\|");
			map.put(UniNames.Flag_E, split[0]);
			map.put(UniNames.Flag_P, split[1]);
			map.put(UniNames.Flag_G, split[2]);
			map.put(UniNames.Flag_T, split[3]);
			map.put(UniNames.Flag_A, split[4]);
		} else {
			map.put(UniNames.Flag, flagPattern);
		}
		map.replaceAll((k, v) -> v.equals("o") ? null : v);
		return map;
	}

	public static List<DataDb> parseListFxToDb(List<CommonModelFx> modelList) {
		final List<DataDb> collect = modelList.stream().map(model -> convertFxToDb((DataFx) model)).collect(Collectors.toList());
		return collect;
	}

	public static List<DataFx> parseListDbToFx(List<? extends CommonDbModel> modelList) {
		final List<DataFx> collect = modelList.stream().map(model -> convertDbToFx((DataDb) model)).collect(Collectors.toList());
		return collect;
	}
}
