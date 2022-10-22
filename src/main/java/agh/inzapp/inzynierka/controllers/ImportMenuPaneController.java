package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.enums.Analysers;
import agh.inzapp.inzynierka.enums.DataType;
import agh.inzapp.inzynierka.exceptions.ApplicationException;
import agh.inzapp.inzynierka.models.modelObj.BaseDataObj;
import agh.inzapp.inzynierka.service.FileChooserRemember;
import agh.inzapp.inzynierka.strategies.CSVImportPQ;
import agh.inzapp.inzynierka.strategies.CSVStrategy;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static agh.inzapp.inzynierka.enums.DataType.*;
import static agh.inzapp.inzynierka.enums.FXMLNames.MAIN;
import static agh.inzapp.inzynierka.enums.FXMLNames.TABLE_VIEW;

@Component
public class ImportMenuPaneController {
	private final ObservableList<File> observableNormalList = FXCollections.observableArrayList();
	private final ObservableList<File> observableHarmonicList = FXCollections.observableArrayList();
	@FXML
	private AnchorPane apMain;
	@FXML
	private ComboBox<Analysers> comboBoxAnalyzer;
	@FXML
	private ListView<File> listViewNormal;
	@FXML
	private ListView<File> listViewHarmonics;
	@FXML
	private Button btnNormalSelect;
	@FXML
	private Button btnHarmonicsSelect;
	@FXML
	private Button btnImport;
	@FXML
	private RadioButton noNormal;
	@FXML
	private RadioButton noHarmonic;
	@FXML
	private RadioButton yesHarmonic;
	@FXML
	private RadioButton yesNormal;
	@FXML
	private TitledPane titledPaneNormal;
	@FXML
	private TitledPane titledPaneHarmonics;
	@FXML
	private Label labelNormal;
	@FXML
	private Label labelHarmonics;

	public void initialize() {
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
				.and(comboBoxAnalyzer.valueProperty().isEqualTo(Analysers.PQbox).or(comboBoxAnalyzer.valueProperty().isEqualTo(Analysers.Sonel))).not()
		);

	}

	@FXML
	void importDataFileNames() {
		getFiles(NORMAL_DATA);
		setObservableFileList(NORMAL_DATA);
	}

	@FXML
	void importHarmonicsFileNames() {
		getFiles(HARMONICS_DATA);
		setObservableFileList(HARMONICS_DATA);
	}

	private void getFiles(DataType dataType) {
		List<File> files = FileChooserRemember.showOpenMultipleDialog();
		if (files != null) {
			files.forEach(file -> {
				switch (dataType) {
					case NORMAL_DATA -> {
						if (!observableNormalList.contains(file)) {
							observableNormalList.add(file);
						}
					}
					case HARMONICS_DATA -> {
						if (!observableHarmonicList.contains(file)) {
							observableHarmonicList.add(file);
						}
					}
				}
			});
		}
	}

	private void setObservableFileList(DataType dataType) {
		if (dataType.equals(NORMAL_DATA)) {
			listViewNormal.setItems(observableNormalList);
		} else if (dataType.equals(HARMONICS_DATA)) {
			listViewHarmonics.setItems(observableHarmonicList);
		}
	}

	@FXML
	private void deleteNormalFileFromListOnAction() {
		File file = listViewNormal.getSelectionModel().getSelectedItem();
//		System.out.println("usuń: " + file);

		if (file != null) {
			listViewNormal.getSelectionModel().clearSelection();
			observableNormalList.remove(file);
			listViewNormal.setItems(observableNormalList);
		}
	}

	@FXML
	private void deleteHarmonicsFileFromListOnAction() {
		File file = listViewHarmonics.getSelectionModel().getSelectedItem();

		if (file != null) {
			listViewHarmonics.getSelectionModel().clearSelection();
			observableHarmonicList.remove(file);
			listViewHarmonics.refresh();
		}
	}

	@FXML
	private void importData() {
		switch (comboBoxAnalyzer.getValue()) {
			case PQbox:
				if (yesNormal.isSelected() && noHarmonic.isSelected()) {
					System.out.println("saving...");
					DataManager.saveAll(getDataList(new CSVImportPQ()));
					System.out.println("done");
				} else if (yesHarmonic.isSelected() && noNormal.isSelected()) {
//					DataManager.saveAll(getDataList(new CSVImportPQHarmonics()));
					//todo pq harmonics import
					System.out.println("pq harmonics import");
				} else if (yesNormal.isSelected() && yesHarmonic.isSelected()) {
//					DataManager.saveAll(getDataList(new CSVImportPQHarmonics()));
//					DataManager.saveAll(getDataList(new CSVImportPQ()));
					//todo pq harmonics import
//					System.out.println("pq harmonics import");
				}
				break;
			case Sonel:
				if (yesHarmonic.isSelected()) {
					//todo sonel import csv
					System.out.println("sonel import");
				} else {
					//todo sonel harmonics import
					System.out.println("sonel harmonics import");
				}
				break;
			default:
				try {
					throw new ApplicationException("Błąd w importData() -> case: default");
				} catch (ApplicationException e) {
					ApplicationException.printDialog(e.getMessage(), e.getClass(), "error.importData");
				}
		}

		try {
			switchToTableViewAferImport();
		} catch (ApplicationException e) {
			ApplicationException.printDialog(e.getMessage(), e.getClass(), "error.switchTableView");
		}
	}

	private List<? extends BaseDataObj> getDataList(CSVStrategy csvStrategy) {
		List<BaseDataObj> modelList = new ArrayList<>();
		observableNormalList.forEach(file ->
		{
			try {
				modelList.addAll(csvStrategy.importCSVFile(file.getAbsolutePath()));
			} catch (ApplicationException e) {
				ApplicationException.printDialog(e.getMessage(), e.getClass(), "error.getDataList");
			}
		});
		return modelList;
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
