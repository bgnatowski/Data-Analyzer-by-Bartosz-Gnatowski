package agh.inzapp.inzynierka.utils;

import agh.inzapp.inzynierka.models.enums.FXMLNames;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FxmlUtils {
	public static Pane fxmlLoad(FXMLNames fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(fxml.getUrl());
		fxmlLoader.setResources(getResourceBundle());
		return fxmlLoader.load();
	}

	public static FXMLLoader getLoader(FXMLNames fxml) {
		FXMLLoader fxmlLoader = new FXMLLoader(fxml.getUrl());
		fxmlLoader.setResources(getResourceBundle());
		return fxmlLoader;
	}

	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("bundles.messages");
	}

	public static String getInternalizedPropertyByKey(String key) {
		return ResourceBundle.getBundle("bundles.messages").getString(key);
	}

	public static Parent openNewSceneFromLoader(FXMLNames fxml, String bundleKey, int height, int width) throws IOException {
		Parent root = FxmlUtils.fxmlLoad(fxml);
		Stage stage = new Stage();
		stage.setTitle(FxmlUtils.getInternalizedPropertyByKey(bundleKey));
		stage.setScene(new Scene(root, width, height));
		stage.show();
		return root;
	}

	public static Parent openNewSceneFromLoader(FXMLNames fxml, String bundleKey) throws IOException {
		Parent root = FxmlUtils.fxmlLoad(fxml);
		Stage stage = new Stage();
		stage.setTitle(FxmlUtils.getInternalizedPropertyByKey(bundleKey));
		stage.setScene(new Scene(root));
		stage.initStyle(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.show();
		return root;
	}

	public static Stage openNewSceneOfPane(Parent root, String bundleKey, int height, int width) {
		Stage stage = new Stage();
		stage.setTitle(FxmlUtils.getInternalizedPropertyByKey(bundleKey));
		stage.setScene(new Scene(root, width, height));
		stage.show();
		return stage;
	}


	public static void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isBefore(minDate)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						} else if (item.isAfter(maxDate)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}
					}
				};
			}
		};
		datePicker.setDayCellFactory(dayCellFactory);
	}
}
