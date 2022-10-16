package agh.inzapp.inzynierka.models.modelFx;

import agh.inzapp.inzynierka.converters.DataConverter;
import agh.inzapp.inzynierka.database.DataDb;
import agh.inzapp.inzynierka.database.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ListDataFx {
	private ObservableList<DataFx> dataFxObservableList = FXCollections.observableArrayList();
	private List<DataFx> dataFxList = new ArrayList<>();

	public void init(){
		List<DataDb> allDataDb = DataManager.getAll();
		dataFxList.clear();
		allDataDb.forEach(dataDb -> {
			final DataFx dataFx = DataConverter.convertDbToFx(dataDb);
			dataFxList.add(dataFx);
		});
		dataFxObservableList.setAll(dataFxList);
	}

	public ObservableList<DataFx> getDataFxObservableList() {
		return dataFxObservableList;
	}

	public void setDataFxObservableList(ObservableList<DataFx> dataFxObservableList) {
		this.dataFxObservableList = dataFxObservableList;
	}

	public List<DataFx> getDataFxList() {
		return dataFxList;
	}

	public void setDataFxList(List<DataFx> dataFxList) {
		this.dataFxList = dataFxList;
	}
}
