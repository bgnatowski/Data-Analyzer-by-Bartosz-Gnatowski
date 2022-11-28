package agh.inzapp.inzynierka.database.models;

import java.time.LocalDate;
import java.time.LocalTime;

public interface CommonDbModel {
	Long getId();
	LocalDate getDate();
	LocalTime getTime();
	String getFlags();
}
