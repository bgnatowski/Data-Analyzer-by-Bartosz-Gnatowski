package agh.inzapp.inzynierka.database.models;

import agh.inzapp.inzynierka.models.enums.UniNames;

import java.time.LocalDateTime;
import java.util.Map;

public interface CommonDbModel {
	Long getId();
	LocalDateTime getDate();
	String getFlags();
	Map<UniNames, Double> getRecords();
}
