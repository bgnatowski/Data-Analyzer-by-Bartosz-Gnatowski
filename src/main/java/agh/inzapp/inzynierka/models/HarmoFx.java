package agh.inzapp.inzynierka.models;

import agh.inzapp.inzynierka.enums.UniNames;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class HarmoFx implements CommonModel{
	private LongProperty id = new SimpleLongProperty();
	private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
	private ObjectProperty<LocalTime> time = new SimpleObjectProperty<>();
	private MapProperty<UniNames, String> flags = new SimpleMapProperty<>();
	private ListProperty<UniNames> columnHarmonicNames = new SimpleListProperty<>();
	private MapProperty<UniNames, Double> thd = new SimpleMapProperty<>();
	private MapProperty<UniNames, Double> harmonics = new SimpleMapProperty<>();


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

	public ObservableList<UniNames> getColumnHarmonicNames() {
		return columnHarmonicNames.get();
	}

	public ListProperty<UniNames> columnHarmonicNamesProperty() {
		return columnHarmonicNames;
	}

	public void setColumnHarmonicNames(ObservableList<UniNames> columnHarmonicNames) {
		this.columnHarmonicNames.set(columnHarmonicNames);
	}

	public ObservableMap<UniNames, Double> getThd() {
		return thd.get();
	}

	public MapProperty<UniNames, Double> thdProperty() {
		return thd;
	}

	public void setThd(ObservableMap<UniNames, Double> thd) {
		this.thd.set(thd);
	}

	public ObservableMap<UniNames, Double> getHarmonics() {
		return harmonics.get();
	}

	public MapProperty<UniNames, Double> harmonicsProperty() {
		return harmonics;
	}

	public void setHarmonics(ObservableMap<UniNames, Double> harmonics) {
		this.harmonics.set(harmonics);
	}

	@Override
	public String toString() {
		List<String> allDataToString = new ArrayList<>();
		allDataToString.add(getDate().toString());
		allDataToString.add(getTime().toString());
		allDataToString.add(getFlags().values().toString());

		String s = Arrays.toString(getHarmonics().values().toArray());
		String records = s.substring(1, s.length()-1);
		allDataToString.add(records);

		String s2 = Arrays.toString(getThd().values().toArray());
		String records2 = s2.substring(1, s.length()-1);
		allDataToString.add(records2);

		return allDataToString.toString();
	}
}
