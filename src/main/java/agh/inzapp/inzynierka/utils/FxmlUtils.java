package agh.inzapp.inzynierka.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

public class FxmlUtils {
	public static Pane fxmlLoad(String fxmlPath) {
		FXMLLoader fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
		fxmlLoader.setResources(getResourceBundle());
		try {
			return fxmlLoader.load();
		} catch (Exception e) {
			DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.fxmlLoad");
		}
		return null;
	}

	public static FXMLLoader getLoader(String fxmlPath) {
		FXMLLoader fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
		fxmlLoader.setResources(getResourceBundle());
		return fxmlLoader;
	}

	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("bundles.messages");
	}

	public static String getInternalizedPropertyByKey(String key) {
		return ResourceBundle.getBundle("bundles.messages").getString(key);
	}

}
