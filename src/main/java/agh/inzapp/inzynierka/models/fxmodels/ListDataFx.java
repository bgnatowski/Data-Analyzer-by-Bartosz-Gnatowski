package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.utils.converters.DataConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListDataFx {
	private ObservableList<DataFx> dataFxObservableList = FXCollections.observableArrayList();
	private List<DataFx> dataFxList = new ArrayList<>();
	private static volatile ListDataFx instance;

	private ListDataFx(){}

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

	public void init() {
		Long startReading = System.currentTimeMillis();
		List<? extends CommonDbModel> allDataDb = DataManager.getAll(DataDb.class);
		Long endReading = System.currentTimeMillis();
		System.out.println("FindAll normal: "+(endReading-startReading));
		if (allDataDb != null){
			dataFxList.clear();
			dataFxList.addAll(DataConverter.parseListDbToFx(allDataDb));
			dataFxObservableList.addAll(dataFxList);
		}
	}

	public void clear(){
		dataFxList.clear();
	}
	public boolean hasRecordOfDate(LocalDateTime testedLocalDateTime){
		if(!dataFxList.isEmpty())
			return dataFxList.stream().anyMatch(record -> record.getDate().isEqual(testedLocalDateTime));
		return false;
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
