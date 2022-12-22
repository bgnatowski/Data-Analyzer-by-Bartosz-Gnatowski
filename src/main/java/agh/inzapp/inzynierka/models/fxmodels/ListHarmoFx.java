package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.utils.converters.DataConverter;
import agh.inzapp.inzynierka.utils.converters.HarmoConverter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListHarmoFx {
	private ObservableList<HarmoFx> harmoFxObservableList = FXCollections.observableArrayList();
	private List<HarmoFx> harmoFxList = new ArrayList<>();
	private static volatile ListHarmoFx instance;

	private ListHarmoFx() {}

	public static ListHarmoFx getInstance(){
		ListHarmoFx result = instance;
		if (result != null) {
			return result;
		}
		synchronized(ListHarmoFx.class) {
			if (instance == null) {
				instance = new ListHarmoFx();
			}
			return instance;
		}
	}

	public static void reset() {
		instance = null;
	}

	public void init(List<CommonModelFx> harmoFxes) {
		Long startReading = System.currentTimeMillis();
		final List<HarmoFx> collect = harmoFxes.stream().map(model -> (HarmoFx) model).collect(Collectors.toList());
		Long endReading = System.currentTimeMillis();
		System.out.println("FindAll harmo: "+(endReading-startReading));
		if (collect != null){
			harmoFxList.clear();
			harmoFxList.addAll(collect);
			harmoFxObservableList.addAll(harmoFxList);
		}
//		Task save = new Task<Void>(){
//			@Override
//			protected Void call() throws Exception {
//				DataManager.clearHarmo();
//				DataManager.saveAll(HarmoConverter.parseListFxToDb(harmoFxes));
//				return null;
//			}
//		};
//		new Thread(save).start();
	}

	public void clear(){
		harmoFxList.clear();
	}
	public boolean hasRecordOfDate(LocalDateTime testedLocalDateTime){
		if(!harmoFxList.isEmpty())
			return harmoFxList.stream().anyMatch(record -> record.getDate().isEqual(testedLocalDateTime));
		return false;
	}

	public ObservableList<HarmoFx> getHarmoFxObservableList() {
		return harmoFxObservableList;
	}

	public void setHarmoFxObservableList(ObservableList<HarmoFx> harmoFxObservableList) {
		this.harmoFxObservableList = harmoFxObservableList;
	}

	public List<HarmoFx> getHarmoFxList() {
		return harmoFxList;
	}

	public void setHarmoFxList(List<HarmoFx> harmoFxList) {
		this.harmoFxList = harmoFxList;
	}

	public static void setInstance(ListHarmoFx instance) {
		ListHarmoFx.instance = instance;
	}
}
