package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Component;

import static agh.inzapp.inzynierka.enums.FXMLNames.*;
@Component
public class MainAppPaneController {
	@FXML
	private MenuButtonsController menuButtonsController;
	@FXML
	private BorderPane borderPane;

	public void initialize(){
		menuButtonsController.setMainController(this);
		setCenter(HOME.getPath());
		setLeft(MENU.getPath());
	}

	private void setLeft(String fxmlPath) {
		borderPane.setLeft(FxmlUtils.fxmlLoad(fxmlPath));
	}

	public void setCenter(String fxmlPath){
		borderPane.setCenter(FxmlUtils.fxmlLoad(fxmlPath));
	}

}
