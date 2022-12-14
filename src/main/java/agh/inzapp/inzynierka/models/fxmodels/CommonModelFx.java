package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.models.enums.UniNames;
import antlr.debug.ParserMatchEvent;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.LocalDateTime;

public abstract class CommonModelFx {
	protected LongProperty id = new SimpleLongProperty();
	protected ObjectProperty<LocalDateTime> date = new SimpleObjectProperty<>();
	protected MapProperty<UniNames, String> flags = new SimpleMapProperty<>();
	protected MapProperty<UniNames, Double> records = new SimpleMapProperty<>();
	protected ListProperty<UniNames> columnNames = new SimpleListProperty<>();

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
}
