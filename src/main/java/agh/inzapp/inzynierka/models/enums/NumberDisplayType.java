package agh.inzapp.inzynierka.models.enums;

import agh.inzapp.inzynierka.utils.FxmlUtils;

public enum NumberDisplayType {
	NORMAL(FxmlUtils.getNameProperty("display.type.normal")),
	SCIENTIFIC(FxmlUtils.getNameProperty("display.type.scientific"));

	private final String displayedName;
	NumberDisplayType(String displayedName) {
		this.displayedName = displayedName;
	}

	@Override
	public String toString() {
		return displayedName;
	}
}
