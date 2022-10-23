package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.enums.Analysers;
import agh.inzapp.inzynierka.exceptions.ApplicationException;
import agh.inzapp.inzynierka.models.FilesList;
import agh.inzapp.inzynierka.models.ListDataFx;
import agh.inzapp.inzynierka.models.ListHarmoFx;
import agh.inzapp.inzynierka.services.ImportManager;
import agh.inzapp.inzynierka.strategies.CSVImportPQ;
import agh.inzapp.inzynierka.strategies.CSVImportPQHarmonics;
import agh.inzapp.inzynierka.utils.CSVUtils;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static agh.inzapp.inzynierka.enums.DataType.HARMONICS_DATA;
import static agh.inzapp.inzynierka.enums.DataType.NORMAL_DATA;
import static agh.inzapp.inzynierka.enums.FXMLNames.MAIN;
import static agh.inzapp.inzynierka.enums.FXMLNames.TABLE_VIEW;

@Component
public class ImportMenuPaneController {
	@FXML
	private AnchorPane apMain;
	@FXML
	private ComboBox<Analysers> comboBoxAnalyzer;
	@FXML
	private ListView<File> listViewNormal, listViewHarmonics;
	@FXML
	private Button btnNormalSelect, btnHarmonicsSelect, btnImport;
	@FXML
	private RadioButton noNormal, noHarmonic, yesHarmonic, yesNormal;;
	@FXML
	private TitledPane titledPaneNormal, titledPaneHarmonics;
	@FXML
	private Label labelNormal, labelHarmonics;
	private FilesList filesList;

	public void initialize() {
		filesList = FilesList.getInstance();
		bindings();
	}

	private void bindings() {
		comboBoxAnalyzer.setItems(FXCollections.observableArrayList(Analysers.PQbox, Analysers.Sonel));
		titledPaneNormal.setExpanded(true);
		titledPaneHarmonics.setExpanded(false);

		labelNormal.disableProperty().bindBidirectional(noNormal.selectedProperty());
		btnNormalSelect.disableProperty().bindBidirectional(noNormal.selectedProperty());
		listViewNormal.disableProperty().bindBidirectional(noNormal.selectedProperty());
		labelHarmonics.disableProperty().bindBidirectional(noHarmonic.selectedProperty());
		btnHarmonicsSelect.disableProperty().bindBidirectional(noHarmonic.selectedProperty());
		listViewHarmonics.disableProperty().bindBidirectional(noHarmonic.selectedProperty());

		btnImport.disableProperty().bind(yesNormal.selectedProperty().or(yesHarmonic.selectedProperty())
				.and((filesList.listNormalProperty().emptyProperty().not().and(yesNormal.selectedProperty().and(noHarmonic.selectedProperty())))
						.or(filesList.listHarmonicsProperty().emptyProperty().not().and(yesHarmonic.selectedProperty())))
				.and(comboBoxAnalyzer.valueProperty().isEqualTo(Analysers.PQbox).or(comboBoxAnalyzer.valueProperty().isEqualTo(Analysers.Sonel))).not());

	}
	@FXML
	void importDataFileNames() {
		filesList.getFiles(NORMAL_DATA);
		listViewNormal.setItems(filesList.getListNormal());
	}
	@FXML
	void importHarmonicsFileNames() {
		filesList.getFiles(HARMONICS_DATA);
		listViewHarmonics.setItems(filesList.getListHarmonics());
	}
	@FXML
	private void deleteNormalFileFromListOnAction() {
		File file = listViewNormal.getSelectionModel().getSelectedItem();

		if (file != null) {
			listViewNormal.getSelectionModel().clearSelection();
			filesList.getListNormal().remove(file);
			listViewNormal.setItems(filesList.getListNormal());
		}
	}
	@FXML
	private void deleteHarmonicsFileFromListOnAction() {
		File file = listViewHarmonics.getSelectionModel().getSelectedItem();

		if (file != null) {
			listViewHarmonics.getSelectionModel().clearSelection();
			filesList.getListHarmonics().remove(file);
			listViewHarmonics.refresh();
		}
	}

	@FXML
	private void importData() {
		switch (comboBoxAnalyzer.getValue()) {
			case PQbox:
				if (yesNormal.isSelected() && noHarmonic.isSelected()) {
					ImportManager.saveNormal(Analysers.PQbox);
				} else if (yesHarmonic.isSelected() && noNormal.isSelected()) {
					ImportManager.saveHarmonics(Analysers.PQbox);
				} else if (yesNormal.isSelected() && yesHarmonic.isSelected()) {
					ImportManager.saveBoth(Analysers.PQbox);
				}
				break;
			case Sonel:
				//todo sonel import
				if (yesNormal.isSelected() && noHarmonic.isSelected()) {
					System.out.println("sonel normal import");
					ImportManager.saveNormal(Analysers.Sonel);
				} else if (yesHarmonic.isSelected() && noNormal.isSelected()) {
					ImportManager.saveHarmonics(Analysers.Sonel);
					System.out.println("sonel harmonics import");
				} else if (yesNormal.isSelected() && yesHarmonic.isSelected()) {
					ImportManager.saveBoth(Analysers.Sonel);
					System.out.println("sonel all import");
				}
		}
		try {
			switchToTableViewAferImport();
		} catch (ApplicationException e) {
			DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.switchTableView");
		}
	}

	private void switchToTableViewAferImport() throws ApplicationException {
		try {
			FXMLLoader loader = FxmlUtils.getLoader(MAIN.getPath());
			Stage stage = (Stage) apMain.getScene().getWindow();
			loader.load();
			Scene scene = new Scene(loader.getRoot(), stage.getWidth(), stage.getHeight());
			stage.setScene(scene);

			MainAppPaneController controller = loader.getController();
			controller.setCenter(TABLE_VIEW.getPath());
		} catch (IOException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

}
