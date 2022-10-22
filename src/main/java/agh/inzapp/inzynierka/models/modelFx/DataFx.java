package agh.inzapp.inzynierka.models.modelFx;

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
import java.util.LinkedHashMap;
import java.util.Map;

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
		map.put(UniNames.U12_avg, null);
		map.put(UniNames.U23_avg, null);
		map.put(UniNames.U31_avg, null);
		map.put(UniNames.UL1_avg, null);
		map.put(UniNames.UL2_avg, null);
		map.put(UniNames.UL3_avg, null);
		map.put(UniNames.UL1_max, null);
		map.put(UniNames.UL2_max, null);
		map.put(UniNames.UL3_max, null);
		map.put(UniNames.UL1_min, null);
		map.put(UniNames.UL2_min, null);
		map.put(UniNames.UL3_min, null);
		map.put(UniNames.IL1_avg, null);
		map.put(UniNames.IL2_avg, null);
		map.put(UniNames.IL3_avg, null);
		map.put(UniNames.IL1_max, null);
		map.put(UniNames.IL2_max, null);
		map.put(UniNames.IL3_max, null);
		map.put(UniNames.IL1_min, null);
		map.put(UniNames.IL2_min, null);
		map.put(UniNames.IL3_min, null);
		map.put(UniNames.IN_avg,  null);
		map.put(UniNames.IN_max,  null);
		map.put(UniNames.IN_min,  null);
		map.put(UniNames.Pst_UL1, null);
		map.put(UniNames.Pst_UL2, null);
		map.put(UniNames.Pst_UL3, null);
		map.put(UniNames.P_total, null);
		map.put(UniNames.P_max,   null);
		map.put(UniNames.P_min,   null);
		map.put(UniNames.S_total, null);
		map.put(UniNames.S_max,   null);
		map.put(UniNames.S_min,   null);
		map.put(UniNames.PF_total,null);
		map.put(UniNames.Q_total,null);
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
}
