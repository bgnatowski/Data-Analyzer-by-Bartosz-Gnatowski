package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static agh.inzapp.inzynierka.models.enums.FXMLNames.*;

@Controller
public class MenuController {
	private static MainViewController mainAppPaneController;
	private static final BooleanProperty toggleButtonProperty = new SimpleBooleanProperty();
	@FXML
	private Button tableViewButton, chartViewButton, reportButton;
	@FXML
	private void goHomeOnAction() {
		mainAppPaneController.setCenter(HOME.getPath());
	}
	@FXML
	private void importCSVMenu() {
		mainAppPaneController.setCenter(IMPORT_MENU.getPath());
	}
	@FXML
	private void informationOnAction() {
		mainAppPaneController.setCenter(INFORMATION.getPath());
	}
	@FXML
	private void tableViewOnAction() {
		mainAppPaneController.setCenter(TABLE_VIEW.getPath());
	}
	@FXML
	private void figuresOnAction() {
		mainAppPaneController.setCenter(CHART_VIEW.getPath());
	}
	@FXML
	private void reportOnAction()  {
		mainAppPaneController.setCenter(REPORT_VIEW.getPath());
	}
	@FXML
	private void exitAppOnAction() {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	private void openInNewWindow() {
		try {
			FxmlUtils.openNewSceneFromLoader(TABLE_VIEW.getPath(), "button.tableview", 450, 450);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@FXML
	void setMainController(MainViewController mainAppPaneController) {
		MenuController.mainAppPaneController = mainAppPaneController;
	}

	@FXML
	public void initialize(){
		toggleButtonProperty.setValue(true);
		tableViewButton.disableProperty().bind(toggleButtonProperty);
		chartViewButton.disableProperty().bind(toggleButtonProperty);
		reportButton.disableProperty().bind(toggleButtonProperty);
	}

	public static void setToggleButtonProperty(boolean toggleButtonProperty) {
		MenuController.toggleButtonProperty.setValue(toggleButtonProperty);
	}
}
