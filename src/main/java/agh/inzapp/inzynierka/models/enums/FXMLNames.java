package agh.inzapp.inzynierka.models.enums;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public enum FXMLNames {
	HOME(Objects.requireNonNull(FXMLNames.class.getResource("/fxml/HomePane.fxml"))),
	IMPORT_MENU(Objects.requireNonNull(FXMLNames.class.getResource(("/fxml/ImportMenuPane.fxml")))),
	MAIN(Objects.requireNonNull(FXMLNames.class.getResource(("/fxml/MainAppPane.fxml")))),
	MENU(Objects.requireNonNull(FXMLNames.class.getResource(("/fxml/MenuButtons.fxml")))),
	TABLE_VIEW(Objects.requireNonNull(FXMLNames.class.getResource(("/fxml/TableViewPane.fxml")))),
	CHART_VIEW(Objects.requireNonNull(FXMLNames.class.getResource(("/fxml/ChartPane.fxml")))),
	REPORT_VIEW(Objects.requireNonNull(FXMLNames.class.getResource(("/fxml/ReportPane.fxml")))),
	INFO_VIEW(Objects.requireNonNull(FXMLNames.class.getResource(("/fxml/InfoPane.fxml")))),
	STANDALONE_CHART_PANE(Objects.requireNonNull(FXMLNames.class.getResource(("/fxml/ChartAnchorPane.fxml"))));

	private final URL url;
	FXMLNames(URL s) {
		url = s;
	}

	public URL getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return url.getPath();
	}
}
