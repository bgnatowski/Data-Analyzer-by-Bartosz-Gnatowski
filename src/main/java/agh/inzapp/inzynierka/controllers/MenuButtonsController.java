package agh.inzapp.inzynierka.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MenuButtonsController {
	static final String HOME_PANE_FXML = "/fxml/HomePane.fxml";
	static final String MENU_BUTTONS_FXML = "/fxml/MenuButtons.fxml";
	static final String MENU_PANE_FXML = "/fxml/ImportMenuPane.fxml";
	static final String INFORMATION_PANE_FXML = "/fxml/InformationPane.fxml";
	static final String TABLE_VIEW_FXML = "/fxml/TableViewPane.fxml";
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
		mainAppPaneController.setCenter(HOME_PANE_FXML);
	}
	@FXML
	private void importCSVMenu(ActionEvent event) {
		mainAppPaneController.setCenter(MENU_PANE_FXML);
	}
	@FXML
	private void informationOnAction() {
		mainAppPaneController.setCenter(INFORMATION_PANE_FXML);
	}

//	static void showTableViewPane(List<? extends BaseDataModelObj> modelsList){
//		mainAppPaneController.setCenter(TABLE_VIEW_FXML);
//	}

	@FXML
	private void exitAppOnAction(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}
	@FXML
	void setMainController(MainAppPaneController mainAppPaneController) {
		this.mainAppPaneController = mainAppPaneController;
	}

	public MainAppPaneController getMainAppPaneController() {
		return mainAppPaneController;
	}
}
