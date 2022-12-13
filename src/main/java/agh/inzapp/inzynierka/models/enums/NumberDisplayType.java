package agh.inzapp.inzynierka.models.enums;

import agh.inzapp.inzynierka.utils.FxmlUtils;

public enum NumberDisplayType {
	NORMAL(FxmlUtils.getInternalizedPropertyByKey("display.type.normal")),
	SCIENTIFIC(FxmlUtils.getInternalizedPropertyByKey("display.type.scientific"));

	private String displayedName;
	NumberDisplayType(String displayedName) {
		this.displayedName = displayedName;
	}

	@Override
	public String toString() {
		return displayedName;
	}
}
