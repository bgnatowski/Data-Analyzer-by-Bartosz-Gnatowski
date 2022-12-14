package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.models.enums.UniNames;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static agh.inzapp.inzynierka.models.enums.UniNames.*;

@Getter
@Setter
@NoArgsConstructor

public class DataFx extends CommonModelFx {
	protected void initCommonRecords() {
		Map<UniNames, Double> map = new LinkedHashMap<>();
		map.put(U12_avg, null);
		map.put(U23_avg, null);
		map.put(U31_avg, null);
		map.put(UL1_avg, null);
		map.put(UL2_avg, null);
		map.put(UL3_avg, null);
		map.put(UL1_max, null);
		map.put(UL2_max, null);
		map.put(UL3_max, null);
		map.put(UL1_min, null);
		map.put(UL2_min, null);
		map.put(UL3_min, null);
		map.put(IL1_avg, null);
		map.put(IL2_avg, null);
		map.put(IL3_avg, null);
		map.put(IL1_max, null);
		map.put(IL2_max, null);
		map.put(IL3_max, null);
		map.put(IL1_min, null);
		map.put(IL2_min, null);
		map.put(IL3_min, null);
		map.put(IN_avg,  null);
		map.put(IN_max,  null);
		map.put(IN_min,  null);
		map.put(Pst_UL1, null);
		map.put(Pst_UL2, null);
		map.put(Pst_UL3, null);
		map.put(P_total, null);
		map.put(P_max,   null);
		map.put(P_min,   null);
		map.put(S_total, null);
		map.put(S_max,   null);
		map.put(S_min,   null);
		map.put(PF_total,null);
		map.put(Q_total,null);
		records.setValue(FXCollections.observableMap(map));
	}

	@Override
	public String toString() {
		List<String> allDataToString = new ArrayList<>();
		allDataToString.add(getDate().toString());
		allDataToString.add(getFlags().values().toString());

		String s = Arrays.toString(getRecords().values().toArray());
		String records = s.substring(1, s.length()-1);
		allDataToString.add(records);

		return allDataToString.toString();
	}
}
