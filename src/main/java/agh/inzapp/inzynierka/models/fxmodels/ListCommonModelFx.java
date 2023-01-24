package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListCommonModelFx {
	private ObservableList<CommonModelFx> modelsFxObservableList;
	private List<CommonModelFx> modelsFx;
	private static volatile ListCommonModelFx instance;
	private static boolean hasBoth;

	private ListCommonModelFx() {
		modelsFx = new ArrayList<>();
		modelsFxObservableList = FXCollections.observableArrayList();
		hasBoth = false;
	}

	public static ListCommonModelFx getInstance() throws ApplicationException {
		ListCommonModelFx result = instance;
		if (result != null) {
			return result;
		}
		synchronized (ListCommonModelFx.class) {
			if (instance == null) {
				instance = new ListCommonModelFx();
			}
			return instance;
		}
	}

	public void addModelList(List<CommonModelFx> incomingModels) {
		if(modelsFx.isEmpty()){
			modelsFx.addAll(incomingModels);
		}else{
			for(int i = 0; i < incomingModels.size(); i++){
				CommonModelFx commonModelFx = incomingModels.get(i);
				modelsFx.forEach(model->{
					if(model.getDate().equals(commonModelFx.getDate())){
						final ObservableList<UniNames> columnNames = model.getColumnNames();
						columnNames.addAll(commonModelFx.getColumnNames());
						final List<UniNames> newColumnNames = columnNames.stream().distinct().toList();
						model.setColumnNames(FXCollections.observableArrayList(newColumnNames));
						model.getRecords().putAll(commonModelFx.getRecords());
						model.getHarmonics().putAll(commonModelFx.getHarmonics());
					}
				});
			}
		}
		modelsFxObservableList.setAll(modelsFx);
	}

	public static void reset(){
		instance = null;
	}

	public static boolean hasBoth() {
		return hasBoth;
	}
	public LocalDateTime getStartDate() {
		if (modelsFxObservableList.isEmpty()) return LocalDateTime.now();
		return modelsFxObservableList.stream().findFirst().orElseThrow().getDate();
	}
	public LocalDateTime getEndDate() {
		if (modelsFxObservableList.isEmpty()) return LocalDateTime.now();
		return modelsFxObservableList.get(modelsFxObservableList.size() - 1).getDate();
	}
	public List<CommonModelFx> getRecordsBetween(LocalDateTime from, LocalDateTime to) throws ApplicationException {
		if (!from.isBefore(to)) throw new ApplicationException("error.date.out.of.range");
		if (modelsFxObservableList.isEmpty()) return modelsFxObservableList;
		return modelsFxObservableList.stream()
				.filter(model -> (model.getDate().isAfter(from) && model.getDate().isBefore(to)))
				.collect(Collectors.toList());
	}
	public List<UniNames> getColumnNames() {
		if (modelsFxObservableList.isEmpty()) return List.of();
		return modelsFxObservableList.stream().findFirst().orElseThrow().getColumnNames();
	}

	public ObservableList<CommonModelFx> getModelsFxObservableList() {
		return modelsFxObservableList;
	}

	public void setModelsFxObservableList(ObservableList<CommonModelFx> modelsFxObservableList) {
		this.modelsFxObservableList = modelsFxObservableList;
	}

	public List<CommonModelFx> getModelsFx() {
		return modelsFx;
	}

	public void setModelsFx(List<CommonModelFx> modelsFx) {
		this.modelsFx = modelsFx;
	}

	public static void setInstance(ListCommonModelFx instance) {
		ListCommonModelFx.instance = instance;
	}

	public static boolean isHasBoth() {
		return hasBoth;
	}

	public static void setHasBoth(boolean hasBoth) {
		ListCommonModelFx.hasBoth = hasBoth;
	}

	public String[] getColumStandardNamesArray() {
		final ObservableList<UniNames> columnNames = modelsFx.get(0).getColumnNames();
		final List<UniNames> harmonicsNames = UniNames.getHarmonics();
		final List<UniNames> availableNames = columnNames.stream().filter(uniName -> !harmonicsNames.contains(uniName)).toList();
		List<String> strings = new ArrayList<>();
		strings.add("id");
		availableNames.forEach(uniNames -> strings.add(uniNames.toString()));
		return strings.toArray(new String[0]);
	}

	public String[] getColumHarmonicNamesArray() {
		final ObservableList<UniNames> columnNames = modelsFx.get(0).getColumnNames();
		final List<UniNames> harmonicsNames = new ArrayList<>();
		harmonicsNames.add(UniNames.Flag_E);
		harmonicsNames.add(UniNames.Flag_P);
		harmonicsNames.add(UniNames.Flag_G);
		harmonicsNames.add(UniNames.Flag_T);
		harmonicsNames.add(UniNames.Flag_A);
		harmonicsNames.add(UniNames.Date);
		harmonicsNames.add(UniNames.Time);
		harmonicsNames.addAll(UniNames.getHarmonics());
		final List<UniNames> availableNames = columnNames.stream().filter(uniName -> harmonicsNames.contains(uniName)).toList();
		List<String> strings = new ArrayList<>();
		strings.add("id");
		availableNames.forEach(uniNames -> strings.add(uniNames.toString()));
		return strings.toArray(new String[0]);
	}

	public ObservableList<UniNames> getColumStandardNames() {
		final ObservableList<UniNames> columnNames = modelsFx.get(0).getColumnNames();
		final List<UniNames> harmonicsNames = UniNames.getHarmonics();
		final List<UniNames> availableNames = columnNames.stream().filter(uniName -> !harmonicsNames.contains(uniName)).toList();
		return FXCollections.observableArrayList(availableNames);
	}

	public ObservableList<UniNames> getColumHarmonicNames() {
		final ObservableList<UniNames> columnNames = modelsFx.get(0).getColumnNames();
		final List<UniNames> harmonicsNames = new ArrayList<>();
		harmonicsNames.add(UniNames.Flag_E);
		harmonicsNames.add(UniNames.Flag_P);
		harmonicsNames.add(UniNames.Flag_G);
		harmonicsNames.add(UniNames.Flag_T);
		harmonicsNames.add(UniNames.Flag_A);
		harmonicsNames.add(UniNames.Date);
		harmonicsNames.add(UniNames.Time);
		harmonicsNames.addAll(UniNames.getHarmonics());
		final List<UniNames> availableNames = columnNames.stream().filter(uniName -> harmonicsNames.contains(uniName)).toList();
		return FXCollections.observableArrayList(availableNames);
	}
}
