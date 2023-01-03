package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.enums.AnalyzersModels;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.ListCommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.TimeSpinner;
import agh.inzapp.inzynierka.services.ReportBarChartService;
import agh.inzapp.inzynierka.services.ReportLineChartService;
import agh.inzapp.inzynierka.services.ReportService;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
import java.util.ArrayList;
import java.util.List;

import static agh.inzapp.inzynierka.utils.FxmlUtils.restrictDatePicker;

@Controller
public class ReportViewController {
	private static final BooleanProperty toggleProperty = new SimpleBooleanProperty(false);
	@FXML
	private GridPane timeGrid;
	@FXML
	private DatePicker dateFrom, dateTo;
	private TimeSpinner timeFrom, timeTo;
	@FXML
	private ComboBox<AnalyzersModels> analyzersModelsComboBox;
	@FXML
	private Button generateButton;
	@FXML
	private TextField switchboard, measurementPoint, serialNumber, author;
	@FXML
	private AnchorPane apForPDFView, apMain;
	@FXML
	private StackPane pane;
	private ListCommonModelFx modelsList;
	private ReportBarChartService barChartService;
	private ReportService reportService;
	private ReportLineChartService reportChartService;

	@FXML
	public void initialize() {
		apMain.disableProperty().bind(toggleProperty);
		try {
			modelsList = ListCommonModelFx.getInstance();

			reportChartService = new ReportLineChartService();
			barChartService = new ReportBarChartService();
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

	private void bindings() {
		analyzersModelsComboBox.getItems().setAll(FXCollections.observableArrayList(AnalyzersModels.values()));
	}

	private void bindDatePickers() {
		LocalDateTime startDate = modelsList.getStartDate();
		LocalDateTime endDate = modelsList.getEndDate();

		restrictDatePicker(dateFrom, startDate.toLocalDate(), endDate.toLocalDate());
		restrictDatePicker(dateTo, startDate.toLocalDate(), endDate.toLocalDate());

		dateFrom.setValue(startDate.toLocalDate());
		dateTo.setValue(endDate.toLocalDate());
	}

	@FXML
	private void generateOnAction() {
		try {
			LocalDateTime from = LocalDateTime.of(dateFrom.getValue(), timeFrom.getValue());
			LocalDateTime to = LocalDateTime.of(dateTo.getValue(), timeTo.getValue());
			final List<CommonModelFx> recordsBetween = modelsList.getRecordsBetween(from, to);

			barChartService.createHarmonicsBarCharts(recordsBetween);
			reportChartService.createLineCharts(recordsBetween);
			List<String> userAdditionalData = getUserEnteredData();
			reportService.generateReport(recordsBetween, userAdditionalData);
		} catch (ApplicationException | IOException e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	private List<String> getUserEnteredData() {
		List<String> userAdditionalData = new ArrayList<>();
		userAdditionalData.add(switchboard.getText());
		userAdditionalData.add(measurementPoint.getText());
		if(analyzersModelsComboBox.getSelectionModel().isEmpty())
			userAdditionalData.add("");
		else
			userAdditionalData.add(analyzersModelsComboBox.getSelectionModel().getSelectedItem().toString());
		userAdditionalData.add(serialNumber.getText());
		userAdditionalData.add(author.getText());
		return userAdditionalData;
	}


}
