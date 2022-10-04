package agh.inzapp.inzynierka.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class FxmlUtils {
	public static Pane fxmlLoad(String fxmlPath) {
		FXMLLoader fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
		fxmlLoader.setResources(getResourceBundle());
		try {
			return fxmlLoader.load();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public static Parent fxmlLoad(URL url) {
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		fxmlLoader.setResources(getResourceBundle());
		try {
			return fxmlLoader.load();
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
