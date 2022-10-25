package agh.inzapp.inzynierka.models.enums;

public enum FXMLNames {
	HOME("/fxml/HomePane.fxml"),
	IMPORT_MENU("/fxml/ImportMenuPane.fxml"),
	INFORMATION("/fxml/InformationPane.fxml"),
	MAIN("/fxml/MainAppPane.fxml"),
	MENU("/fxml/MenuButtons.fxml"),
	TABLE_VIEW("/fxml/TableViewPane.fxml");

	private final String path;
	FXMLNames(String s) {
		path = s;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return path;
	}
}
