package agh.inzapp.inzynierka.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

import static agh.inzapp.inzynierka.enums.FXMLNames.*;

@Component
public class MenuButtonsController {
	static final String HOME_FXML = HOME.getPath();
	static final String IMPORT_MENU_FXML = IMPORT_MENU.getPath();
	static final String INFORMATION_FXML = INFORMATION.getPath();
	static final String TABLE_VIEW_FXML = TABLE_VIEW.getPath();
	private static MainAppPaneController mainAppPaneController;
	@FXML
	private Button exitButton;
	@FXML
	private Button homeButton;
	@FXML
	private Button infoButton;
	@FXML
	private Button loadButton;

	@FXML
	private void goHomeOnAction() {
		mainAppPaneController.setCenter(HOME_FXML);
	}
	@FXML
	private void importCSVMenu() {
		mainAppPaneController.setCenter(IMPORT_MENU_FXML);
	}
	@FXML
	private void informationOnAction() {
		mainAppPaneController.setCenter(INFORMATION_FXML);
	}
	@FXML
	private void tableViewOnAction() {
		mainAppPaneController.setCenter(TABLE_VIEW_FXML);
	}
	@FXML
	private void figuresOnAction() {
	}
	@FXML
	private void exitAppOnAction() {
		Platform.exit();
		System.exit(0);
	}
	@FXML
	void setMainController(MainAppPaneController mainAppPaneController) {
		this.mainAppPaneController = mainAppPaneController;
	}
}
