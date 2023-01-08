package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.models.enums.UniNames;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
		final List<HarmoFx> collect = harmoFxes.stream().map(model -> (HarmoFx) model).toList();
		harmoFxList.clear();
		harmoFxList.addAll(collect);
		harmoFxObservableList.addAll(harmoFxList);
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

	public String[] getColumNamesArray(){
		final ObservableList<UniNames> columnNames = harmoFxList.get(0).getColumnNames();
		List<String> strings = new ArrayList<>();
		strings.add("id");
		columnNames.forEach(uniNames -> strings.add(uniNames.toString()));
		return strings.toArray(new String[0]);
	}
}
