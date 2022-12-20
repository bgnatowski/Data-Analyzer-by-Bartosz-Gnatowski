package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.enums.AnalyzersModels;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.*;
import agh.inzapp.inzynierka.services.BarChartService;
import agh.inzapp.inzynierka.services.LineChartService;
import agh.inzapp.inzynierka.services.ReportService;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static agh.inzapp.inzynierka.models.enums.UniNames.*;
import static agh.inzapp.inzynierka.utils.FxmlUtils.restrictDatePicker;

@Controller
public class ReportViewController {
	private static final BooleanProperty toggleProperty = new SimpleBooleanProperty(false);
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
	private AnchorPane apForPDFView, apMain;
	@FXML
	private StackPane pane;
	private TimeSpinner timeFrom, timeTo;
	private List<? extends CommonModelFx> modelsList;
	private LineChartService chartService;
	private BarChartService barChartService;
	private ReportService reportService;

	@FXML
	public void initialize() {
		apMain.disableProperty().bind(toggleProperty);
		try {
			modelsList = CommonUtils.mergeFxModelLists();

			chartService = new LineChartService();
			barChartService = new BarChartService();
			reportService = new ReportService();

			addTimeSpinnersToGrid();
			bindDatePickers();
			bindings();
			toggleProperty.set(false);
		} catch (ApplicationException e) {
			toggleProperty.set(true);
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	private void bindings() {
		modelComboBox.getItems().setAll(FXCollections.observableArrayList(AnalyzersModels.values()));
	}

	private void addTimeSpinnersToGrid() {
		timeFrom = new TimeSpinner();
		timeFrom.setId("timeSpinnerFrom");
		timeFrom.maxWidth(Double.MAX_VALUE);
		timeTo = new TimeSpinner();
		timeTo.setId("timeSpinnerTo");
		timeTo.maxWidth(Double.MAX_VALUE);

		timeGrid.add(timeFrom, 0, 2);
		timeGrid.add(timeTo, 1, 2);
	}

	private void bindDatePickers() {
		LocalDateTime startDate = modelsList.get(0).getDate();
		LocalDateTime endDate = modelsList.get(modelsList.size() - 1).getDate();

		restrictDatePicker(dateFrom, startDate.toLocalDate(), endDate.toLocalDate());
		restrictDatePicker(dateTo, startDate.toLocalDate(), endDate.toLocalDate());

		dateFrom.setValue(startDate.toLocalDate());
		dateTo.setValue(endDate.toLocalDate());
	}

	@FXML
	private void generateOnAction() {
		try {
			generateBarCharts();
		} catch (ApplicationException | IOException e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	private void generateBarCharts() throws ApplicationException, IOException {
		LocalDateTime from = LocalDateTime.of(dateFrom.getValue(), timeFrom.getValue());
		LocalDateTime to = LocalDateTime.of(dateTo.getValue(), timeTo.getValue());
		if (from.isBefore(to)) {
			final List<CommonModelFx> collect = modelsList.stream()
					.filter(model -> (model.getDate().isAfter(from) && model.getDate().isBefore(to)))
					.collect(Collectors.toList());
			barChartService.createHarmonicsBarCharts(collect);
		} else {
			throw new ApplicationException("error.bar.chart.generate");
		}
	}


}
