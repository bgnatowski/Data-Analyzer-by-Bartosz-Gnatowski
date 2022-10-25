package agh.inzapp.inzynierka.models;

import agh.inzapp.inzynierka.models.enums.DataType;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public class FilesList {
	private final ListProperty<File> listNormal = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ListProperty<File> listHarmonics = new SimpleListProperty<>(FXCollections.observableArrayList());
	private static FilesList instance;

	private FilesList() {
	}

	public static FilesList getInstance() {
		FilesList result = instance;
		if (result != null) {
			return result;
		}
		synchronized(ListHarmoFx.class) {
			if (instance == null) {
				instance = new FilesList();
			}
			return instance;
		}
	}

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
