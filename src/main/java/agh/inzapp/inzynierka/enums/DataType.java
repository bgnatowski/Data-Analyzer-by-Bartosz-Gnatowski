package agh.inzapp.inzynierka.enums;

import lombok.Getter;
import lombok.Setter;

@Getter

public enum DataType {
	HARMONICS_DATA("fileChooser.harmonics"),
	NORMAL_DATA("fileChooser.data");

	DataType(String title) {
		this.title = title;
	}

	final String title;
}