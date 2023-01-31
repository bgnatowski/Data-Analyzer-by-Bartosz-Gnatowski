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
public class CommonModelFx implements Cloneable{
	private LongProperty id = new SimpleLongProperty();
	private ObjectProperty<LocalDateTime> date = new SimpleObjectProperty<>();
	private MapProperty<UniNames, String> flags = new SimpleMapProperty<>(FXCollections.observableMap(new LinkedHashMap<>()));
	private MapProperty<UniNames, Double> records = new SimpleMapProperty<>();
	private MapProperty<UniNames, Double> harmonics = new SimpleMapProperty<>();
	private ListProperty<UniNames> columnNames = new SimpleListProperty<>();

	//copy constructor
	public CommonModelFx(CommonModelFx dfx) {
		setId(dfx.getId());
		setDate(dfx.getDate());
		setFlags(dfx.getFlags());
		final ObservableMap<UniNames, Double> recordsN = dfx.getRecords();
		LinkedHashMap<UniNames, Double> newRecordsMap = new LinkedHashMap<>(recordsN);
		setRecords(FXCollections.observableMap(newRecordsMap));

		final ObservableMap<UniNames, Double> harmonicN = dfx.getHarmonics();
		LinkedHashMap<UniNames, Double> newHarmonicsMap = new LinkedHashMap<>(recordsN);
		setHarmonics(FXCollections.observableMap(newHarmonicsMap));

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

	public void deleteNone() {
		if(columnNames.contains(UniNames.NONE)){
			columnNames.removeAll(UniNames.NONE);
		}
	}
}
