package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.models.enums.UniNames;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.LocalDateTime;

public interface CommonModelFx {
	long getId();
	LocalDateTime getDate();
	ObservableMap<UniNames, String> getFlags();
	ObservableList<UniNames> getColumnNames();
	ObservableMap<UniNames, Double> getRecords();
}
