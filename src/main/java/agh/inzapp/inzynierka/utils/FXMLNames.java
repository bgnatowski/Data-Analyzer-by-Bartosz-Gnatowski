package agh.inzapp.inzynierka.utils;

public enum FXMLNames {
	Home("/fxml/HomePane.fxml"),
	ImportMenu("/fxml/ImportMenuPane.fxml"),
	Information("/fxml/InformationPane.fxml"),
	Main("/fxml/MainAppPane.fxml"),
	Menu("/fxml/MenuButtons.fxml"),
	DataView("/fxml/TableViewPane.fxml");

	private String path;
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
