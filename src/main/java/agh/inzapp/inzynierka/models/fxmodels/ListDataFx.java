package agh.inzapp.inzynierka.models.fxmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
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

	public static void reset() {
		instance = null;
	}

	public void init(List<CommonModelFx> normalFxes) {
		final List<DataFx> collect = normalFxes.stream().map(model -> (DataFx) model).toList();
		dataFxList.clear();
		dataFxList.addAll(collect);
		dataFxObservableList.addAll(dataFxList);

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
