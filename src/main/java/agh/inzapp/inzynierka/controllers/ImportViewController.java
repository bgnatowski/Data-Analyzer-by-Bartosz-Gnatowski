package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.CsvFilesList;
import agh.inzapp.inzynierka.models.enums.Analysers;
import agh.inzapp.inzynierka.models.fxmodels.ListCommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.ListDataFx;
import agh.inzapp.inzynierka.models.fxmodels.ListHarmoFx;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

import static agh.inzapp.inzynierka.models.enums.DataType.HARMONICS_DATA;
import static agh.inzapp.inzynierka.models.enums.DataType.NORMAL_DATA;
import static agh.inzapp.inzynierka.models.enums.FXMLNames.MAIN;
import static agh.inzapp.inzynierka.models.enums.FXMLNames.TABLE_VIEW;

@Controller
public class ImportViewController {
	@FXML
	private AnchorPane apMain;
	@FXML
	private ComboBox<Analysers> comboBoxAnalyzer;
	@FXML
	private ListView<File> listViewNormal, listViewHarmonics;
	@FXML
	private Button btnNormalSelect, btnHarmonicsSelect, btnImport;
	@FXML
	private RadioButton noNormal, noHarmonic, yesHarmonic, yesNormal;
	@FXML
	private TitledPane titledPaneNormal, titledPaneHarmonics;
	@FXML
	private Label labelNormal, labelHarmonics;
	private CsvFilesList filesList;

	public void initialize() {
		filesList = new CsvFilesList();
		bindings();

		//testing only
//		btnImport.setDisable(false);
//		yesNormal.fire();
//		yesHarmonic.fire();
//		File normalTestFile = new File("E:\\glowneRepo\\inz\\src\\main\\resources\\data\\OchotnicaTrafo.csv");
//		File harmoTestFile = new File("E:\\glowneRepo\\inz\\src\\main\\resources\\data\\OchotnicaTrafo_Uharmo.csv");
//		filesList.addTestFiles(normalTestFile, harmoTestFile);
//		listViewNormal.getItems().add(normalTestFile);
//		listViewHarmonics.getItems().add(harmoTestFile);
//		//
	}

	private void bindings() {
		comboBoxAnalyzer.setItems(FXCollections.observableArrayList(Analysers.PQbox, Analysers.Sonel));
		titledPaneNormal.setExpanded(true);
		titledPaneHarmonics.setExpanded(true);
		//list bindings
		labelNormal.disableProperty().bindBidirectional(noNormal.selectedProperty());
		btnNormalSelect.disableProperty().bindBidirectional(noNormal.selectedProperty());
		listViewNormal.disableProperty().bindBidirectional(noNormal.selectedProperty());
		labelHarmonics.disableProperty().bindBidirectional(noHarmonic.selectedProperty());
		btnHarmonicsSelect.disableProperty().bindBidirectional(noHarmonic.selectedProperty());
		listViewHarmonics.disableProperty().bindBidirectional(noHarmonic.selectedProperty());
		// button binding
		btnImport.disableProperty().bind(yesNormal.selectedProperty().or(yesHarmonic.selectedProperty())
				.and(
						(filesList.listNormalProperty().emptyProperty().not().and(yesNormal.selectedProperty().and(noHarmonic.selectedProperty())))
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
		try {
			ListCommonModelFx.reset();
			clearUploaded();
			importDataFromAnalyser();
			switchToTableViewAferImport();
		} catch (ApplicationException e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	@FXML
	private void addNormalFileFromListOnAction() {
		importDataFileNames();
	}

	@FXML
	private void addHarmonicsFileFromListOnAction() {
		importHarmonicsFileNames();
	}

	private void clearUploaded() {
		ListHarmoFx.reset();
		ListDataFx.reset();
	}

	private void importDataFromAnalyser() throws ApplicationException {
		Analysers analyser = comboBoxAnalyzer.getValue();
		if (yesNormal.isSelected() && noHarmonic.isSelected()) {
			filesList.saveNormal(analyser);
		} else if (yesHarmonic.isSelected() && noNormal.isSelected()) {
			filesList.saveHarmonics(analyser);
		} else if (yesNormal.isSelected() && yesHarmonic.isSelected()) {
			filesList.saveBoth(analyser);
		}
	}
	private void switchToTableViewAferImport() throws ApplicationException {
		try {
			FXMLLoader loader = FxmlUtils.getLoader(MAIN);
			Stage stage = (Stage) apMain.getScene().getWindow();
			loader.load();
			Scene scene = new Scene(loader.getRoot(), stage.getWidth(), stage.getHeight());
			stage.setScene(scene);

			MainViewController controller = loader.getController();
			controller.setCenter(TABLE_VIEW);
			MenuController.setToggleButtonProperty(false);
		} catch (IOException e) {
			throw new ApplicationException("error.switchTableView");
		}
	}
}
