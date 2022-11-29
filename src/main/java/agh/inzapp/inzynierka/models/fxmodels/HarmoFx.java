package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.models.enums.UniNames;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

public class HarmoFx implements CommonModelFx {
	private LongProperty id = new SimpleLongProperty();
	private ObjectProperty<LocalDateTime> date = new SimpleObjectProperty<>();
	private MapProperty<UniNames, String> flags = new SimpleMapProperty<>();
	private ListProperty<UniNames> columnNames = new SimpleListProperty<>();
	private MapProperty<UniNames, Double> records = new SimpleMapProperty<>();

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

	public ObservableList<UniNames> getColumnNames() {
		return columnNames.get();
	}

	public ListProperty<UniNames> columnNamesProperty() {
		return columnNames;
	}

	public void setColumnNames(ObservableList<UniNames> columnNames) {
		this.columnNames.set(columnNames);
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


}
