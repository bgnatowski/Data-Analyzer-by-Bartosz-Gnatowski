package agh.inzapp.inzynierka.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.util.ResourceBundle;

public class DialogUtils {
	private static final ResourceBundle bundle = FxmlUtils.getResourceBundle();

	public static void errorDialog(String errorMessage){
		Alert errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setTitle(bundle.getString("error.title"));
		errorAlert.setHeaderText(bundle.getString("error.header"));
		TextArea textArea = new TextArea(errorMessage);
		textArea.setEditable(false);
		errorAlert.getDialogPane().setContent(textArea);
		errorAlert.showAndWait();
	}
	public static void errorDialog(String errorMessage, Class<? extends Exception> aClass, String bundleResourceKey) {
		StringBuilder sb = new StringBuilder();
		final String internalizedPropertyByKey = FxmlUtils.getInternalizedPropertyByKey(bundleResourceKey);
		sb.append(errorMessage)
				.append(". \nCaused in class: ")
				.append(aClass.getSimpleName())
				.append(". \n")
				.append(internalizedPropertyByKey);
		DialogUtils.errorDialog(sb.toString());
	}
}
