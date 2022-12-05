package agh.inzapp.inzynierka.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class FxmlUtils {
	public static Pane fxmlLoad(String fxmlPath) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
		fxmlLoader.setResources(getResourceBundle());
		return fxmlLoader.load();
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

	public static void openNewSceneFromLoader(String panePath, String bundleKey, int height, int width) {
		Parent root;
		try {
			root = FxmlUtils.fxmlLoad(panePath);
			Stage stage = new Stage();
			stage.setTitle(FxmlUtils.getInternalizedPropertyByKey(bundleKey));
			stage.setScene(new Scene(root, width, height));
			stage.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Stage openNewSceneOfPane(Parent root, String bundleKey, int height, int width) {
		Stage stage = new Stage();
		stage.setTitle(FxmlUtils.getInternalizedPropertyByKey(bundleKey));
		stage.setScene(new Scene(root, width, height));
		stage.show();
		return stage;
	}


}
