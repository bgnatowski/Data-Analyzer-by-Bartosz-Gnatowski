package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.enums.Analysers;
import agh.inzapp.inzynierka.exceptions.ApplicationException;
import agh.inzapp.inzynierka.models.modelObj.BaseDataObj;
import agh.inzapp.inzynierka.strategies.CSVImportPQ;
import agh.inzapp.inzynierka.strategies.CSVStrategy;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static agh.inzapp.inzynierka.enums.FXMLNames.MAIN;
import static agh.inzapp.inzynierka.enums.FXMLNames.TABLE_VIEW;

@Component
public class ImportMenuPaneController {
	final private FileChooser fc = new FileChooser();
	private ObservableList<File> filesNormalDataList = FXCollections.observableArrayList();
	private ObservableList<File> filesHarmonicsDataList = FXCollections.observableArrayList();

	@Getter
	private enum DataType {
		HARMONICS_DATA("fileChooser.harmonics"),
		NORMAL_DATA("fileChooser.data");

		DataType(String title) {
			this.title = title;
		}

		final String title;
	}

	@FXML
	private AnchorPane ap;
	@FXML
	private ComboBox<Analysers> comboBoxAnalyzer;
	@FXML
	private Label labelImportNormal;
	@FXML
	private Label labelImportHarmonics;
	@FXML
	private Label getLabelImportNormal;
	@FXML
	private Button btnDataImport;
	@FXML
	private Button btnHarmonicsImport;
	@FXML
	private Button importButton;
	@FXML
	private ListView<File> multiFilesNormalDataListView;
	@FXML
	private ListView<File> multiFilesHarmonicsDataListView;
	@FXML
	private Label questionLabelHarmonics;
	@FXML
	private Label questionLabelNormal;
	@FXML
	private RadioButton radioButtonNoHarmonic;
	@FXML
	private RadioButton radioButtonYesHarmonic;
	@FXML
	private RadioButton radioButtonYesNormal;
	@FXML
	private RadioButton radioButtonNoNormal;
	@FXML
	private TitledPane titledPaneNormal;
	@FXML
	private TitledPane titledPaneHarmonics;
	@FXML
	private ToggleGroup normalGroup;
	@FXML
	private ToggleGroup harmonicsGroup;


	public void initialize() {
		bindings();
	}

	private void bindings() {
		comboBoxAnalyzer.setItems(FXCollections.observableArrayList(Analysers.PQbox, Analysers.Sonel));
		titledPaneNormal.setExpanded(true);
		titledPaneHarmonics.setExpanded(false);

		labelImportNormal.disableProperty().bindBidirectional(radioButtonNoNormal.selectedProperty());
		btnDataImport.disableProperty().bindBidirectional(radioButtonNoNormal.selectedProperty());
		multiFilesNormalDataListView.disableProperty().bindBidirectional(radioButtonNoNormal.selectedProperty());
//
		labelImportHarmonics.disableProperty().bindBidirectional(radioButtonNoHarmonic.selectedProperty());
		btnHarmonicsImport.disableProperty().bindBidirectional(radioButtonNoHarmonic.selectedProperty());
		multiFilesHarmonicsDataListView.disableProperty().bindBidirectional(radioButtonNoHarmonic.selectedProperty());

		importButton.disableProperty().bind(radioButtonYesNormal.selectedProperty().or(radioButtonYesHarmonic.selectedProperty())
				.and(comboBoxAnalyzer.valueProperty().isEqualTo(Analysers.PQbox).or(comboBoxAnalyzer.valueProperty().isEqualTo(Analysers.Sonel))).not()
		);

	}

	@FXML
	void importDataFileNames() {
		prepareFileChooser(DataType.NORMAL_DATA);
		getFileNames(DataType.NORMAL_DATA);
	}

	@FXML
	void importHarmonicsFileNames() {
		prepareFileChooser(DataType.HARMONICS_DATA);
		getFileNames(DataType.HARMONICS_DATA);
	}

	private void prepareFileChooser(DataType dataType) {
		fc.setTitle(FxmlUtils.getInternalizedPropertyByKey(dataType.getTitle()));
		fc.setInitialDirectory(new File(System.getProperty("user.dir")));
		fc.getExtensionFilters().clear();
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
				FxmlUtils.getResourceBundle().getString("fileChooser.extension"), "*.csv");
		fc.getExtensionFilters().add(extensionFilter);
	}

	private void getFileNames(DataType dataType) {
		List<File> files = fc.showOpenMultipleDialog(null);
		if (files != null) {
			files.forEach(file -> {
				if (file != null) {
					switch (dataType) {
						case NORMAL_DATA -> {
							if (!filesNormalDataList.contains(file)) {
								filesNormalDataList.add(file);
							}
						}
						case HARMONICS_DATA -> {
							if (!filesHarmonicsDataList.contains(file)) {
								filesHarmonicsDataList.add(file);
							}
						}
					}
					System.out.println(file);
				} else {
					try {
						throw new ApplicationException("Invalid file. File = null.");
					} catch (ApplicationException e) {
						DialogUtils.errorDialog(e.getMessage());
					}
				}
			});
		}
		if (dataType.equals(DataType.NORMAL_DATA)) {
			multiFilesNormalDataListView.setItems(filesNormalDataList);
		} else if (dataType.equals(DataType.HARMONICS_DATA)) {
			multiFilesHarmonicsDataListView.setItems(filesHarmonicsDataList);
		}
	}

	@FXML
	private void deleteNormalFileFromListOnAction() {
		File file = multiFilesNormalDataListView.getSelectionModel().getSelectedItem();
		System.out.println("usuń: " + file);

		if (file != null) {
			multiFilesNormalDataListView.getSelectionModel().clearSelection();
			filesNormalDataList.remove(file);
			multiFilesNormalDataListView.setItems(filesNormalDataList);
		}
	}

	@FXML
	private void deleteHarmonicsFileFromListOnAction() {
		File file = multiFilesHarmonicsDataListView.getSelectionModel().getSelectedItem();

		if (file != null) {
			multiFilesHarmonicsDataListView.getSelectionModel().clearSelection();
			filesHarmonicsDataList.remove(file);
			multiFilesHarmonicsDataListView.refresh();
		}
	}

	@FXML
	private void importData() {
		List<? extends BaseDataObj> modelsList;
		switch (comboBoxAnalyzer.getValue()) {
			case PQbox:
				if (radioButtonNoHarmonic.isSelected()) {
					modelsList = getDataList(new CSVImportPQ());
					System.out.println("saving...");
					DataManager.saveAll(modelsList);
					System.out.println("done");
				} else {
//					modelsList = getDataList(new CSVFromPQHarmonics);
//					//todo pq harmonics import
					System.out.println("pq harmonics import");
				}
				break;
			case Sonel:
				if (radioButtonYesHarmonic.isSelected()) {
//						modelsList = getDataList(new CSVFromSonel());
					//todo sonel import csv
					System.out.println("sonel import");
				} else {
//						modelsList = getDataList(new CSVFromSonelHarmonics);
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
		filesNormalDataList.forEach(file ->
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
			Stage stage = (Stage) ap.getScene().getWindow();
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
