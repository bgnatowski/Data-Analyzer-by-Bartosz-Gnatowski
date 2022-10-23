package agh.inzapp.inzynierka.models;

import agh.inzapp.inzynierka.converters.DataConverter;
import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ListDataFx {
	private ObservableList<DataFx> dataFxObservableList = FXCollections.observableArrayList();
	private List<DataFx> dataFxList = new ArrayList<>();

	private static volatile ListDataFx instance;

	private ListDataFx(){
	}

	public static ListDataFx getInstance(){
		ListDataFx result = instance;
		if (result != null) {
			return result;
		}
		synchronized(ListDataFx.class) {
			if (instance == null) {
				instance = new ListDataFx();
			}
			return instance;
		}
	}

	public void init() {
		List<? extends CommonDbModel> allDataDb = DataManager.getAll(DataDb.class);
		if (allDataDb != null){
			dataFxList.clear();
			allDataDb.forEach(dataDb -> {
				if (dataDb instanceof DataDb){
					DataFx dataFx = DataConverter.convertDbToFx((DataDb) dataDb);
					dataFxList.add(dataFx);
				}
			});
			dataFxObservableList.setAll(dataFxList);
		}

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
