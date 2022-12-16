package agh.inzapp.inzynierka.models;

import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.models.enums.Analysers;
import agh.inzapp.inzynierka.models.enums.DataType;
import agh.inzapp.inzynierka.models.fxmodels.*;
import agh.inzapp.inzynierka.strategies.*;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CsvFilesList {
	private ListProperty<File> listNormal = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<File> listHarmonics = new SimpleListProperty<>(FXCollections.observableArrayList());
	public void getFiles(DataType dataType) {
		List<File> files = FileChooserRemember.showOpenMultipleDialog();
		if (files != null) {
			files.forEach(file -> {
				switch (dataType) {
					case NORMAL_DATA -> {
						if (!listNormal.contains(file)) {
							listNormal.add(file);
						}
					}
					case HARMONICS_DATA -> {
						if (!listHarmonics.contains(file)) {
							listHarmonics.add(file);
						}
					}
				}
			});
		}
	}

	public void addTestFiles(File normal, File harmo){
		listNormal.add(normal);
		listHarmonics.add(harmo);
	}

	public void saveNormal(Analysers analyser) throws ApplicationException {
		DataManager.clearNormal();
		List<CommonModelFx> normalFxes = List.of();
		switch (analyser){
			case PQbox -> {
				Long startReading = System.currentTimeMillis();
				normalFxes= new ArrayList<>(importNormalDataList(new CSVImportPQ()));
//				DataManager.saveAll(importNormalDataList(new CSVImportPQ()));
				Long endReading = System.currentTimeMillis();
				System.out.println("Save normal: "+(endReading-startReading));
			}
			case Sonel -> normalFxes= new ArrayList<>(importNormalDataList(new CSVImportSonel()));
		}
		ListDataFx.getInstance().init(normalFxes);
	}
	public void saveHarmonics(Analysers analyser) throws ApplicationException {
		DataManager.clearHarmo();
		List<CommonModelFx> harmoFxes = List.of();
		switch (analyser){
			case PQbox -> harmoFxes = new ArrayList<>(importHarmonicsDataList(new CSVImportPQHarmonics()));
			case Sonel -> harmoFxes = new ArrayList<>(importHarmonicsDataList(new CSVImportSonelHarmonics()));
		}
		ListHarmoFx.getInstance().init(harmoFxes);
	}

	public void saveBoth(Analysers analyser) throws ApplicationException {
		DataManager.clearNormal();
		DataManager.clearHarmo();
		switch (analyser){
			case PQbox ->{
				Long startReading = System.currentTimeMillis();
				List<CommonModelFx> normalFxes = importNormalDataList(new CSVImportPQ());
//				DataManager.saveAll(importNormalDataList(new CSVImportPQ()));
				Long endReading = System.currentTimeMillis();
				System.out.println("Save normal: "+(endReading-startReading));

				ListDataFx.getInstance().init(normalFxes);

				startReading = System.currentTimeMillis();
				final List<CommonModelFx> harmoFxes = importHarmonicsDataList(new CSVImportPQHarmonics());
//				DataManager.saveAll(importHarmonicsDataList(new CSVImportPQHarmonics()));
				endReading = System.currentTimeMillis();
				System.out.println("Save harmo: "+(endReading-startReading));

				ListHarmoFx.getInstance().init(harmoFxes);
			}
			case Sonel -> {
//				DataManager.saveAll(importNormalDataList(new CSVImportSonel()));
//				DataManager.saveAll(importHarmonicsDataList(new CSVImportSonelHarmonics()));
			}
		}
	}
	private List<CommonModelFx> importNormalDataList(CSVStrategy csvStrategy) {
		List<CommonModelFx> modelList = new ArrayList<>();
		listNormal.forEach(file ->
		{
			try {
				modelList.addAll(csvStrategy.importCSVFile(file.getAbsolutePath()));
			} catch (ApplicationException e) {
				DialogUtils.errorDialog(e.getMessage());
			}
		});
		return modelList;
	}

	private List<CommonModelFx> importHarmonicsDataList(CSVStrategy csvStrategy) {
		List<CommonModelFx> modelList = new ArrayList<>();
		listHarmonics.forEach(file ->
		{
			try {
				modelList.addAll(csvStrategy.importCSVFile(file.getAbsolutePath()));
			} catch (ApplicationException e) {
				DialogUtils.errorDialog(e.getMessage());
			}
		});
		return modelList;
	}
	public ObservableList<File> getListNormal() {
		return listNormal.get();
	}

	public ListProperty<File> listNormalProperty() {
		return listNormal;
	}

	public void setListNormal(ObservableList<File> listNormal) {
		this.listNormal.set(listNormal);
	}

	public ObservableList<File> getListHarmonics() {
		return listHarmonics.get();
	}

	public ListProperty<File> listHarmonicsProperty() {
		return listHarmonics;
	}

	public void setListHarmonics(ObservableList<File> listHarmonics) {
		this.listHarmonics.set(listHarmonics);
	}
}
