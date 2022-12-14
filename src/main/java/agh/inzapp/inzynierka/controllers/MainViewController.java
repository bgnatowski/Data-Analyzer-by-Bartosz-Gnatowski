package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static agh.inzapp.inzynierka.models.enums.FXMLNames.*;
@Controller
public class MainViewController {
	@FXML
	private MenuController menuButtonsController;
	@FXML
	private BorderPane borderPane;

	@FXML
	public void initialize(){
		menuButtonsController.setMainController(this);
		setCenter(HOME.getPath());
		setLeft(MENU.getPath());
	}

	private void setLeft(String fxmlPath) {
		try {
			borderPane.setLeft(FxmlUtils.fxmlLoad(fxmlPath));
		} catch (IOException e) {
			DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.fxmlLoad");
		}
	}

	public void setCenter(String fxmlPath){
		try {
			borderPane.setCenter(FxmlUtils.fxmlLoad(fxmlPath));
		} catch (IOException e) {
			DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.fxmlLoad");
		}
	}


}
