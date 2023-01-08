package agh.inzapp.inzynierka.models;

import agh.inzapp.inzynierka.models.enums.Analysers;
import agh.inzapp.inzynierka.models.enums.DataType;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.ListDataFx;
import agh.inzapp.inzynierka.models.fxmodels.ListHarmoFx;
import agh.inzapp.inzynierka.strategies.*;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFilesList {
	private final ListProperty<File> listNormal = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ListProperty<File> listHarmonics = new SimpleListProperty<>(FXCollections.observableArrayList());
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

	public void addNormal(File file){
		listNormal.add(file);
	}

	public void addHarmo(File file){
		listHarmonics.add(file);
	}

	public void saveNormal(Analysers analyser) {
		List<CommonModelFx> normalFxes = List.of();
		switch (analyser){
			case PQbox -> normalFxes= new ArrayList<>(importNormalDataList(new CSVImportPQ()));
			case Sonel -> normalFxes= new ArrayList<>(importNormalDataList(new CSVImportSonel()));
		}
		ListDataFx.getInstance().init(normalFxes);
	}
	public void saveHarmonics(Analysers analyser) {
		List<CommonModelFx> harmoFxes = List.of();
		switch (analyser){
			case PQbox -> harmoFxes = new ArrayList<>(importHarmonicsDataList(new CSVImportPQHarmonics()));
			case Sonel -> harmoFxes = new ArrayList<>(importHarmonicsDataList(new CSVImportSonelHarmonics()));
		}
		ListHarmoFx.getInstance().init(harmoFxes);
	}

	public void saveBoth(Analysers analyser){
		switch (analyser){
			case PQbox ->{
				List<CommonModelFx> normalFxes = importNormalDataList(new CSVImportPQ());
				ListDataFx.getInstance().init(normalFxes);

				List<CommonModelFx> harmoFxes = importHarmonicsDataList(new CSVImportPQHarmonics());
				ListHarmoFx.getInstance().init(harmoFxes);
			}
			case Sonel -> {
				List<CommonModelFx> normalFxes = importNormalDataList(new CSVImportSonel());
				ListDataFx.getInstance().init(normalFxes);

				List<CommonModelFx> harmoFxes = importHarmonicsDataList(new CSVImportSonelHarmonics());
				ListHarmoFx.getInstance().init(harmoFxes);
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
