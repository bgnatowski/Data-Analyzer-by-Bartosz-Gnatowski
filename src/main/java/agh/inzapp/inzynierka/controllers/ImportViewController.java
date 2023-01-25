package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.CsvFilesList;
import agh.inzapp.inzynierka.models.enums.Analysers;
import agh.inzapp.inzynierka.models.fxmodels.ListCommonModelFx;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Controller;

import java.io.File;

import static agh.inzapp.inzynierka.models.enums.FXMLNames.TABLE_VIEW;

@Controller
public class ImportViewController {
	@FXML
	private AnchorPane apMain;
	@FXML
	private HBox progressBar;
	@FXML
	private ComboBox<Analysers> comboBoxAnalyzer;
	@FXML
	private ListView<File> listView;
	@FXML
	private Button btnImport, btnClear;
	private CsvFilesList filesList;

	@FXML
	public void initialize() {
		filesList = CsvFilesList.getInstance();
		listView.getItems().addAll(filesList.getFilesList());
		bindings();
	}

	private void bindings() {
		btnImport.setDisable(false);
		comboBoxAnalyzer.setItems(FXCollections.observableArrayList(Analysers.PQbox, Analysers.Sonel));
		btnImport.disableProperty().bind(filesList.filesListProperty().emptyProperty().not().and(
				comboBoxAnalyzer.valueProperty().isEqualTo(Analysers.PQbox).or(comboBoxAnalyzer.valueProperty().isEqualTo(Analysers.Sonel))).not()
		);
	}

	@FXML
	void chooseFilesOnAction() {
		filesList.getFilesFromChooser();
		updateListView();
	}

	@FXML
	private void deleteFileFromListOnAction() {
		File file = listView.getSelectionModel().getSelectedItem();

		if (file != null) {
			listView.getSelectionModel().clearSelection();
			filesList.getFilesList().remove(file);
			updateListView();
		}
	}

	@FXML
	private void importDataModelOnAction() {
		progressBar.setVisible(true);
		ListCommonModelFx.reset();
		clearUploaded();
		importDataFromAnalyser();
	}

	@FXML
	private void popupAddFileOnAction() {
		chooseFilesOnAction();
	}

	private void clearUploaded() {
		ListCommonModelFx.reset();
	}

	private void importDataFromAnalyser() {
		Task task = new Task<Void>() {
			@Override public Void call() throws ApplicationException {
				Analysers analyser = comboBoxAnalyzer.getValue();
				filesList.saveBoth(analyser);
				return null;
			}

		};
		task.exceptionProperty().addListener((observable, oldValue, newValue) ->  {
			if(newValue instanceof ApplicationException) {
				ApplicationException ex = (ApplicationException) newValue;
				progressBar.setVisible(false);
				DialogUtils.errorDialog(ex.getMessage());
			}
		});
		task.setOnSucceeded(e -> {
			switchToTableViewAferImport();
		});
		new Thread(task).start();
	}

	private void switchToTableViewAferImport() {
		MenuController.switchSceneTo(TABLE_VIEW);
		MenuController.setToggleButtonProperty(false);
	}

	@FXML
	private void clearOnAction() {
		filesList.clear();
		clearUploaded();
		updateListView();
		MenuController.setToggleButtonProperty(true);
	}

	@FXML
	private void onDragDroppedNormal(DragEvent event) {
		final File file = event.getDragboard().getFiles().get(0);
		if (file.getName().endsWith(".csv")) {
			filesList.addFileToList(file);
			updateListView();
		}
	}

	private void updateListView() {
		listView.setItems(filesList.getFilesList());
	}

	@FXML
	private void onDragOverNormal(DragEvent event) {
		if (event.getDragboard().hasFiles()) {
			event.acceptTransferModes(TransferMode.LINK);
		}
	}
}
