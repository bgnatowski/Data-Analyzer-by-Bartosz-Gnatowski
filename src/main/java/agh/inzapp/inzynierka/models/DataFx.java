package agh.inzapp.inzynierka.models;

import agh.inzapp.inzynierka.enums.UniNames;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static agh.inzapp.inzynierka.enums.UniNames.*;

@Getter
@Setter
@NoArgsConstructor

public class DataFx {
	private LongProperty id = new SimpleLongProperty();
	private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
	private ObjectProperty<LocalTime> time = new SimpleObjectProperty<>();
	private MapProperty<UniNames, String> flags = new SimpleMapProperty<>();
	private MapProperty<UniNames, Double> records = new SimpleMapProperty<>();
	private ListProperty<UniNames> columnNames = new SimpleListProperty<>();

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

	public long getId() {
		return id.get();
	}

	public LongProperty idProperty() {
		return id;
	}

	public void setId(long id) {
		this.id.set(id);
	}

	public LocalDate getDate() {
		return date.get();
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
	}

	public LocalTime getTime() {
		return time.get();
	}

	public ObjectProperty<LocalTime> timeProperty() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time.set(time);
	}

	public ObservableMap<UniNames, String> getFlags() {
		return flags.get();
	}

	public MapProperty<UniNames, String> flagsProperty() {
		return flags;
	}

	public void setFlags(ObservableMap<UniNames, String> flags) {
		this.flags.set(flags);
	}

	public ObservableMap<UniNames, Double> getRecords() {
		return records.get();
	}

	public MapProperty<UniNames, Double> recordsProperty() {
		return records;
	}

	public void setRecords(ObservableMap<UniNames, Double> records) {
		this.records.set(records);
	}

	public ObservableList<UniNames> getColumnNames() {
		return columnNames.get();
	}

	public ListProperty<UniNames> columnNamesProperty() {
		return columnNames;
	}

	public void setColumnNames(ObservableList<UniNames> columnNames) {
		this.columnNames.set(columnNames);
	}

	@Override
	public String toString() {
		List<String> allDataToString = new ArrayList<>();
		allDataToString.add(getDate().toString());
		allDataToString.add(getTime().toString());
		allDataToString.add(getFlags().values().toString());

		String s = Arrays.toString(getRecords().values().toArray());
		String records = s.substring(1, s.length()-1);
		allDataToString.add(records);

		return allDataToString.toString();
	}
}
