package agh.inzapp.inzynierka.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class HomePaneController {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private ImageView imageView;
	@FXML
	private HBox hbox;

	public void initialize(){;
	}
}
