package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ListCommonModelFx {
	private final ObservableList<CommonModelFx> modelsFxObservableList = FXCollections.observableArrayList();
	private final List<CommonModelFx> modelList = new ArrayList<>();
	private static volatile ListCommonModelFx instance;

	private ListCommonModelFx(){
	}

	public static ListCommonModelFx getInstance() throws ApplicationException {
		ListCommonModelFx result = instance;
		if (result != null) {
			return result;
		}
		synchronized(ListCommonModelFx.class) {
			if (instance == null) {
				instance = new ListCommonModelFx();
				instance.init();
			}
			return instance;
		}
	}

	private void init() throws ApplicationException {
		final List<CommonModelFx> commonModelFxes = mergeFxModelLists();
		modelList.clear();
		modelList.addAll(commonModelFxes);
		modelsFxObservableList.addAll(modelList);
	}

	private List<CommonModelFx> mergeFxModelLists() throws ApplicationException {
		ListDataFx listDataFx = ListDataFx.getInstance();
		ListHarmoFx listHarmoFx = ListHarmoFx.getInstance();

		List<DataFx> dataFxList = Objects.requireNonNull(listDataFx).getDataFxList();
		List<HarmoFx> harmoFxList = Objects.requireNonNull(listHarmoFx).getHarmoFxList();
		if(dataFxList.size() != harmoFxList.size()) throw new ApplicationException(FxmlUtils.getInternalizedPropertyByKey("error.merge.list"));

		List<CommonModelFx> commonList = new ArrayList<>();
		for(int i = 0; i < dataFxList.size(); i++){
			DataFx dfx = (DataFx) dataFxList.get(i).clone();
			HarmoFx hfx = (HarmoFx) harmoFxList.get(i).clone();
			CommonModelFx common = new CommonModelFx(dfx, hfx);
			commonList.add(common);
		}
		return commonList;
	}

	public LocalDateTime getStartDate() {
		if(modelsFxObservableList.isEmpty()) return LocalDateTime.now();
		return modelsFxObservableList.stream().findFirst().orElseThrow().getDate();
	}


	public LocalDateTime getEndDate() {
		if(modelsFxObservableList.isEmpty()) return LocalDateTime.now();
		return modelsFxObservableList.get(modelsFxObservableList.size() - 1).getDate();
	}

	public List<CommonModelFx> getRecordsBetween(LocalDateTime from, LocalDateTime to) throws ApplicationException {
		if (!from.isBefore(to)) throw new ApplicationException("error.date.out.of.range");
		if(modelsFxObservableList.isEmpty()) return modelsFxObservableList;
		return modelsFxObservableList.stream()
				.filter(model -> (model.getDate().isAfter(from) && model.getDate().isBefore(to)))
				.collect(Collectors.toList());
	}

	public List<UniNames> getColumnNames() {
		if(modelsFxObservableList.isEmpty()) return List.of();
		return modelsFxObservableList.stream().findFirst().orElseThrow().getColumnNames();
	}
}
