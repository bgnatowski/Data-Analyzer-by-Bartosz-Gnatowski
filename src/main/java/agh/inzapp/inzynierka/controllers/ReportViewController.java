package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.enums.AnalyzersModels;
import agh.inzapp.inzynierka.models.fxmodels.*;
import agh.inzapp.inzynierka.services.ChartService;
import agh.inzapp.inzynierka.utils.DialogUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static agh.inzapp.inzynierka.utils.FxmlUtils.restrictDatePicker;

@Controller
public class ReportViewController {
	@FXML
	private GridPane timeGrid;
	@FXML
	private DatePicker dateFrom, dateTo;
	@FXML
	private ComboBox<AnalyzersModels> modelComboBox;
	@FXML
	private Button generateButton;
	@FXML
	private TextField switchboard, measurementPoint, serialNumber;
	@FXML
	private AnchorPane apForPDFView;
	private TimeSpinner timeFrom, timeTo;

	private List<DataFx> dataFxList;
	private List<HarmoFx> harmoFxList;
	private ChartService chartService;


	@FXML
	public void initialize() {
		chartService = new ChartService();
		ListDataFx listDataFx = ListDataFx.getInstance();
		ListHarmoFx listHarmoFx = ListHarmoFx.getInstance();

		dataFxList = Objects.requireNonNull(listDataFx).getDataFxList();
		harmoFxList = Objects.requireNonNull(listHarmoFx).getHarmoFxList();
		addTimeSpinnersToGrid();
		bindDatePickers();
		bindings();
	}

	private void bindings() {
		modelComboBox.getItems().setAll(FXCollections.observableArrayList(AnalyzersModels.values()));
	}

	private void addTimeSpinnersToGrid() {
		timeFrom = new TimeSpinner();
		timeFrom.setId("timeSpinnerFrom");
		timeFrom.maxWidth(Double.MAX_VALUE);
		timeGrid.add(timeFrom, 0, 2);
		timeTo = new TimeSpinner();
		timeTo.setId("timeSpinnerTo");
		timeTo.maxWidth(Double.MAX_VALUE);
		timeGrid.add(timeTo, 1, 2);
	}

	private void bindDatePickers() {
		LocalDateTime startDate = LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now();
		if (isBothListPresent()) {
			startDate = dataFxList.get(0).getDate();
			endDate = dataFxList.get(dataFxList.size() - 1).getDate();
		} else if (isOnlyNormalDataPresent()) {
			startDate = dataFxList.get(0).getDate();
			endDate = dataFxList.get(dataFxList.size() - 1).getDate();
		} else if (isOnlyHarmoDataPresent()) {
			startDate = harmoFxList.get(0).getDate();
			endDate = harmoFxList.get(harmoFxList.size() - 1).getDate();
		}

		restrictDatePicker(dateFrom, startDate.toLocalDate(), endDate.toLocalDate());
		restrictDatePicker(dateTo, startDate.toLocalDate(), endDate.toLocalDate());
		dateFrom.setValue(startDate.toLocalDate());
		dateTo.setValue(endDate.toLocalDate());
	}


	@FXML
	private void generateOnAction(ActionEvent event) {
	}

	private boolean isOnlyHarmoDataPresent() {
		return dataFxList.isEmpty() && !harmoFxList.isEmpty();
	}

	private boolean isOnlyNormalDataPresent() {
		return !dataFxList.isEmpty() && harmoFxList.isEmpty();
	}

	private boolean isBothListPresent() {
		return !dataFxList.isEmpty() && !harmoFxList.isEmpty();
	}
}
