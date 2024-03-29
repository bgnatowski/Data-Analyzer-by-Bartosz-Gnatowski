package agh.inzapp.inzynierka.models.enums;

import lombok.Getter;

@Getter

public enum DataType {
	HARMONICS_DATA("fileChooser.harmonics"),
	NORMAL_DATA("fileChooser.data");

	DataType(String title) {
		this.title = title;
	}

	final String title;
}