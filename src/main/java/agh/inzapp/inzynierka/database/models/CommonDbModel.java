package agh.inzapp.inzynierka.database.models;

import java.time.LocalDateTime;

public interface CommonDbModel {
	Long getId();
	LocalDateTime getDate();
	String getFlags();
}
