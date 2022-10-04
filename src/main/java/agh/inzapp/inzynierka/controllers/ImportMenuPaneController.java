package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.database.dbmodels.PQDataDb;
import agh.inzapp.inzynierka.database.repository.PQRepository;
import agh.inzapp.inzynierka.models.modelObj.BaseDataModelObj;
import agh.inzapp.inzynierka.models.modelObj.PQDataObj;
import agh.inzapp.inzynierka.strategies.CSVFromPQ;
import agh.inzapp.inzynierka.strategies.CSVStrategy;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.converters.PQConverter;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@Component()
public class ImportMenuPaneController {
	@Autowired
	PQRepository pqRepository;
	final private FileChooser fc = new FileChooser();
	public static final String FILE_CHOOSER_TITLE_DATA = "fileChooser.data";
	private static final String FILE_CHOOSER_HARMONICS_DATA = "fileChooser.harmonics";
	private ObservableList<File> filesNormalDataList = FXCollections.observableArrayList();
	private ObservableList<File> filesHarmonicsDataList = FXCollections.observableArrayList();

	private enum DataType {
		HARMONICS_DATA,
		NORMAL_DATA;

	}

	@FXML
	private ComboBox<String> comboBoxAnalyzer;

	@FXML
	private Label labelImport;
	@FXML
	private Button btnDataImport;
	@FXML
	private Label harmonicsLabel;
	@FXML
	private Button btnHarmonicsImport;
	@FXML
	private Button importButton;
	@FXML
	private ListView<File> multiFilesNormalDataListView;
	@FXML
	private ListView<File> multiFilesHarmonicsDataListView;
	@FXML
	private Label questionLabel;
	@FXML
	private RadioButton radioButtonNo;
	@FXML
	private RadioButton radioButtonYes;

	public void initialize() {
		comboBoxAnalyzer.setItems(FXCollections.observableArrayList("WinPQ mobil", "Sonel Analiza - PQM-711"));
		bindings();
	}

	private void bindings() {
		labelImport.disableProperty().bind(comboBoxAnalyzer.valueProperty().isNull());
		btnDataImport.disableProperty().bind(comboBoxAnalyzer.valueProperty().isNull());
		multiFilesNormalDataListView.disableProperty().bind(comboBoxAnalyzer.valueProperty().isNull());

		harmonicsLabel.disableProperty().bind(radioButtonNo.selectedProperty());
		btnHarmonicsImport.disableProperty().bind(radioButtonNo.selectedProperty());
		multiFilesHarmonicsDataListView.disableProperty().bind(radioButtonNo.selectedProperty());

		questionLabel.disableProperty().bind(comboBoxAnalyzer.valueProperty().isNull());
		radioButtonNo.disableProperty().bind(comboBoxAnalyzer.valueProperty().isNull());
		radioButtonYes.disableProperty().bind(comboBoxAnalyzer.valueProperty().isNull());

		importButton.disableProperty().bind(comboBoxAnalyzer.valueProperty().isNull());
		//todo importButton should be disabled when not done

	}

	@FXML
	void importDataFileNames(ActionEvent event) {
		prepareFileChooser(FILE_CHOOSER_TITLE_DATA);
		getFileNames(DataType.NORMAL_DATA);
	}

	@FXML
	void importHarmonicsFileNames(ActionEvent event) {
		prepareFileChooser(FILE_CHOOSER_HARMONICS_DATA);
		getFileNames(DataType.HARMONICS_DATA);
	}

	private void prepareFileChooser(String title) {
		fc.setTitle(FxmlUtils.getResourceBundle().getString(title));
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

	public void deleteNormalFileFromListOnAction(ActionEvent actionEvent) {
		File file = multiFilesNormalDataListView.getSelectionModel().getSelectedItem();
		System.out.println("usun: " + file);

		if (file != null) {
			multiFilesNormalDataListView.getSelectionModel().clearSelection();
			filesNormalDataList.remove(file);
			multiFilesNormalDataListView.setItems(filesNormalDataList);
		}
	}

	public void deleteHarmonicsFileFromListOnAction(ActionEvent actionEvent) {
		File file = multiFilesHarmonicsDataListView.getSelectionModel().getSelectedItem();
		System.out.println("usun: " + file);

		if (file != null) {
			multiFilesHarmonicsDataListView.getSelectionModel().clearSelection();
			filesHarmonicsDataList.remove(file);
			multiFilesHarmonicsDataListView.refresh();
		}
	}

	@FXML
	private void importData(ActionEvent event) {
		List<BaseDataModelObj> modelsList = null;
		switch (comboBoxAnalyzer.getValue()) {
			case "WinPQ mobil":
				if (radioButtonNo.isSelected()) {
					modelsList = getDataList(new CSVFromPQ());

					System.out.println("saving...");
					modelsList.forEach(model -> {
						PQDataObj modelObj = (PQDataObj) model;
						PQDataDb dbModel = new PQDataDb();
						dbModel.setDate(modelObj.getLocalDateTime().toLocalDate());
						dbModel.setTime(modelObj.getLocalDateTime().toLocalTime());
						dbModel.setFlag(modelObj.getFlag());
						dbModel.setRecords(PQConverter.convertRecordsMapToDb(modelObj.getRecords()));
						pqRepository.save(dbModel);
					});
					System.out.println("done");
				} else {
//					modelsList = getWinPQHamronicsModel();
				}
				break;
//			case "Sonel Analiza - PQM-711":
//					if (radioButtonYes.isSelected()){
//						modelsList = getSonelModelsWithHarmonics();
//					} else {
//						modelsList = getSonelModelsWithoutHarmonics();
//					}
//				break;
			default:
				//TODO do bundlesow
				DialogUtils.errorDialog("Takiego urządzenia nie obsługuję");
		}
		;
//		MenuButtonsController.showTableViewPane(modelsList);
	}

	private List<BaseDataModelObj> getDataList(CSVStrategy csvStrategy) {
		List<BaseDataModelObj> modelList = new ArrayList<>();
		filesNormalDataList.forEach(file ->
		{
			try {
				modelList.addAll(csvStrategy.importCSVFile(file.getAbsolutePath()));
			} catch (ApplicationException e) {
				DialogUtils.errorDialog(e.getMessage());
			}
		});
		return modelList;
	}

}
