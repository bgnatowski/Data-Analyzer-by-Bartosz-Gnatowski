package agh.inzapp.inzynierka.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.util.ResourceBundle;

public class DialogUtils {
	private static ResourceBundle bundle = FxmlUtils.getResourceBundle();

	public static void errorDialog(String errorMessage){
		Alert errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setTitle(bundle.getString("error.title"));
		errorAlert.setHeaderText(bundle.getString("error.header"));
		TextArea textArea = new TextArea(errorMessage);
		textArea.setEditable(false);
		errorAlert.getDialogPane().setContent(textArea);
		errorAlert.showAndWait();
	}
}
