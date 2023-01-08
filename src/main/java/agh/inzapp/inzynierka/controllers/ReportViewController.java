package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.enums.AnalyzersModels;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.ListCommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.TimeSpinner;
import agh.inzapp.inzynierka.services.ReportBarChartService;
import agh.inzapp.inzynierka.services.ReportLineChartService;
import agh.inzapp.inzynierka.services.ReportService;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static agh.inzapp.inzynierka.utils.FxmlUtils.getInternalizedPropertyByKey;
import static agh.inzapp.inzynierka.utils.FxmlUtils.restrictDatePicker;

@Controller
public class ReportViewController {
	private final BooleanProperty toggleProperty = new SimpleBooleanProperty(false);
	@FXML
	private GridPane timeGrid;
	@FXML
	private DatePicker dateFrom, dateTo;
	private TimeSpinner timeFrom, timeTo;
	@FXML
	private ComboBox<AnalyzersModels> analyzersModelsComboBox;
	@FXML
	private Button saveButton;
	@FXML
	private TextField switchboard, measurementPoint, serialNumber, author;
	@FXML
	private AnchorPane apMain;
	@FXML
	private Label info;
	private ListCommonModelFx modelsList;
	private ReportBarChartService barChartService;
	private ReportService reportService;
	private ReportLineChartService reportChartService;
	private String tmpReportPath;

	@FXML
	public void initialize() {
		apMain.disableProperty().bind(toggleProperty);
		if(ListCommonModelFx.hasBoth()){
			DialogUtils.errorDialog(FxmlUtils.getInternalizedPropertyByKey("error.merge.list"));
			toggleProperty.set(false);
		}
		else toggleProperty.set(true);

		try {
			modelsList = ListCommonModelFx.getInstance();

			reportChartService = new ReportLineChartService();
			barChartService = new ReportBarChartService();
			reportService = new ReportService();
			saveButton.disableProperty().bind(reportService.toggleButtonPropertyProperty());
			addTimeSpinnersToGrid();
			bindDatePickers();
			bindings();
		} catch (ApplicationException e) {
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
			tmpReportPath = reportService.generateReport(recordsBetween, userAdditionalData);
			info.setText(FxmlUtils.getInternalizedPropertyByKey("report.info.succes"));
		} catch (ApplicationException | IOException e) {
			info.setText(FxmlUtils.getInternalizedPropertyByKey("error.default"));
			DialogUtils.errorDialog(getInternalizedPropertyByKey(e.getMessage()));
		}
	}

	private List<String> getUserEnteredData() {
		List<String> userAdditionalData = new ArrayList<>();
		if (!switchboard.getText().isEmpty()) userAdditionalData.add(switchboard.getText());
		else userAdditionalData.add(FxmlUtils.getInternalizedPropertyByKey("report.default.electric.switchboard"));

		if (!measurementPoint.getText().isEmpty()) userAdditionalData.add(measurementPoint.getText());
		else userAdditionalData.add(FxmlUtils.getInternalizedPropertyByKey("report.default.measurement.point"));

		if (analyzersModelsComboBox.getSelectionModel().isEmpty()) userAdditionalData.add(FxmlUtils.getInternalizedPropertyByKey("report.default.analyzer"));
		else userAdditionalData.add(analyzersModelsComboBox.getSelectionModel().getSelectedItem().toString());

		if (!serialNumber.getText().isEmpty()) userAdditionalData.add(serialNumber.getText());
		else userAdditionalData.add(FxmlUtils.getInternalizedPropertyByKey("report.default.analyzer.series"));

		if (!author.getText().isEmpty()) userAdditionalData.add(author.getText());
		else userAdditionalData.add(FxmlUtils.getInternalizedPropertyByKey("report.default.author"));

		return userAdditionalData;
	}


	@FXML
	private void saveAs() {
		try {
			SavingUtils.saveReport(tmpReportPath);
			info.setText(FxmlUtils.getInternalizedPropertyByKey("report.info.saved"));
		} catch (Docx4JException | IOException e) {
			info.setText(FxmlUtils.getInternalizedPropertyByKey("error.default"));
		}
	}
}
