package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import static agh.inzapp.inzynierka.controllers.MenuButtonsController.HOME_PANE_FXML;
import static agh.inzapp.inzynierka.controllers.MenuButtonsController.MENU_BUTTONS_FXML;

public class MainAppPaneController {
	@FXML
	private MenuButtonsController menuButtonsController;
	@FXML
	private BorderPane borderPane;

	public void initialize(){
		menuButtonsController.setMainController(this);
		setCenter(HOME_PANE_FXML);
		setLeft(MENU_BUTTONS_FXML);
	}

	private void setLeft(String fxmlPath) {
		borderPane.setLeft(FxmlUtils.fxmlLoader(fxmlPath));
	}

	public void setCenter(String fxmlPath){
		borderPane.setCenter(FxmlUtils.fxmlLoader(fxmlPath));
	}

}
