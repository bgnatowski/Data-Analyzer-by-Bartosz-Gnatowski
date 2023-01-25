package agh.inzapp.inzynierka.models;

import agh.inzapp.inzynierka.models.enums.Analysers;
import agh.inzapp.inzynierka.models.fxmodels.ListCommonModelFx;
import agh.inzapp.inzynierka.strategies.CSVImportPQ;
import agh.inzapp.inzynierka.strategies.CSVImportSonel;
import agh.inzapp.inzynierka.strategies.CSVStrategy;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class CsvFilesList {
	private final ListProperty<File> filesList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private static CsvFilesList instance;
	public static CsvFilesList getInstance() {
		CsvFilesList result = instance;
		if (result != null) {
			return result;
		}
		synchronized (CsvFilesList.class) {
			if (instance == null) {
				instance = new CsvFilesList();
			}
			return instance;
		}
	}
	public void getFilesFromChooser() {
		List<File> files = FileChooserRemember.showOpenMultipleDialog();
		if (files != null) {
			files.forEach(file -> {
				if (!filesList.contains(file)) {
					filesList.add(file);
				}
			});
		}
	}
	public void addFileToList(File file){
		if (!filesList.contains(file)) {
			filesList.add(file);
		}
	}
	public void clear() {
		filesList.clear();
	}

	public void saveBoth(Analysers analyser) throws ApplicationException {
		final ListCommonModelFx modelsFxList = ListCommonModelFx.getInstance();
		switch (analyser){
			case PQbox -> {
				for (File file : filesList) {
					modelsFxList.addModelList(new CSVImportPQ().importCSVFile(file.getAbsolutePath()));
				}
			}
			case Sonel -> {
				for (File file : filesList) {
					modelsFxList.addModelList(new CSVImportSonel().importCSVFile(file.getAbsolutePath()));
				}
			}
		}
	}

	public ObservableList<File> getFilesList() {
		return filesList.get();
	}

	public ListProperty<File> filesListProperty() {
		return filesList;
	}

	public void setFilesList(ObservableList<File> filesList) {
		this.filesList.set(filesList);
	}
}
