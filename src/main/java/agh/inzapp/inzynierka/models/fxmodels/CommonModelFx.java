package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.DialogUtils;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class CommonModelFx {
	protected LongProperty id = new SimpleLongProperty();
	protected ObjectProperty<LocalDateTime> date = new SimpleObjectProperty<>();
	protected MapProperty<UniNames, String> flags = new SimpleMapProperty<>(FXCollections.observableMap(new LinkedHashMap<>()));
	protected MapProperty<UniNames, Double> records = new SimpleMapProperty<>();
	protected MapProperty<UniNames, Double> harmonics = new SimpleMapProperty<>();
	protected ListProperty<UniNames> columnNames = new SimpleListProperty<>();

	public CommonModelFx(DataFx dfx, HarmoFx hfx) {
		setId(dfx.getId());
		setDate(dfx.getDate());
		setFlags(dfx.getFlags());
		final ObservableMap<UniNames, Double> recordsN = dfx.getRecords();
		final ObservableMap<UniNames, Double> recordsH = hfx.getRecords();
		LinkedHashMap<UniNames, Double> linkedHashMap = new LinkedHashMap<>();
		linkedHashMap.putAll(recordsN);
		linkedHashMap.putAll(recordsH);
		setRecords(FXCollections.observableMap(linkedHashMap));

		final ObservableList<UniNames> columnNamesN = dfx.getColumnNames();
		final ObservableList<UniNames> columnNamesH = hfx.getColumnNames();
		ArrayList<UniNames> columnNames = new ArrayList<>();
		columnNames.addAll(columnNamesN);
		columnNames.addAll(columnNamesH);
		final List<UniNames> columnNamesDistinct = columnNames.stream().distinct().collect(Collectors.toList());
		setColumnNames(FXCollections.observableArrayList(columnNamesDistinct));
	}

	public CommonModelFx(CommonModelFx dfx) {
		setId(dfx.getId());
		setDate(dfx.getDate());
		setFlags(dfx.getFlags());
		final ObservableMap<UniNames, Double> recordsN = dfx.getRecords();
		LinkedHashMap<UniNames, Double> linkedHashMap = new LinkedHashMap<>(recordsN);
		setRecords(FXCollections.observableMap(linkedHashMap));

		final ObservableList<UniNames> columnNamesN = dfx.getColumnNames();
		ArrayList<UniNames> columnNames = new ArrayList<>(columnNamesN);
		final List<UniNames> columnNamesDistinct = columnNames.stream().distinct().collect(Collectors.toList());
		setColumnNames(FXCollections.observableArrayList(columnNamesDistinct));
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

	public long getId() {
		return id.get();
	}

	public LongProperty idProperty() {
		return id;
	}

	public void setId(long id) {
		this.id.set(id);
	}

	public LocalDateTime getDate() {
		return date.get();
	}

	public ObjectProperty<LocalDateTime> dateProperty() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date.set(date);
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
		allDataToString.add(String.valueOf(getId()));
		allDataToString.add(getDate().toString());
		allDataToString.add(getFlags().values().toString());

		String s = Arrays.toString(getRecords().values().toArray());
		String records = s.substring(1, s.length()-1);
		allDataToString.add(records);

		return allDataToString.toString();
	}

	@Override
	public Object clone() {
		CommonModelFx clone = null;
		try{
			clone = (CommonModelFx) super.clone();
		} catch (CloneNotSupportedException e){
			DialogUtils.errorDialog("error.clone");
		}
		return clone;
	}
}
