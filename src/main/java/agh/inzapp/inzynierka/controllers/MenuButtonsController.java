package agh.inzapp.inzynierka.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Controller;

import static agh.inzapp.inzynierka.models.enums.FXMLNames.*;

@Controller
public class MenuButtonsController {
	static final String HOME_FXML = HOME.getPath();
	static final String IMPORT_MENU_FXML = IMPORT_MENU.getPath();
	static final String INFORMATION_FXML = INFORMATION.getPath();
	static final String TABLE_VIEW_FXML = TABLE_VIEW.getPath();
	static final String CHART_VIEW_FXML = CHART_VIEW.getPath();;
	private static MainAppPaneController mainAppPaneController;
	@FXML
	private Button exitButton, homeButton, infoButton, loadButton;
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
		mainAppPaneController.setCenter(CHART_VIEW_FXML);
	}
	@FXML
	private void exitAppOnAction() {
		Platform.exit();
		System.exit(0);
	}
	@FXML
	void setMainController(MainAppPaneController mainAppPaneController) {
		MenuButtonsController.mainAppPaneController = mainAppPaneController;
	}
}
