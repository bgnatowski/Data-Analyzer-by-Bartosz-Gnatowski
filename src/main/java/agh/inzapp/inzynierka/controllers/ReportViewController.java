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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static agh.inzapp.inzynierka.utils.FxmlUtils.getInternalizedPropertyByKey;
import static agh.inzapp.inzynierka.utils.FxmlUtils.restrictDatePicker;

@Controller
public class ReportViewController {
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
	@FXML
	private ProgressIndicator progress;
	@FXML
	private VBox vBox;

	private ListCommonModelFx modelsList;
	private ReportBarChartService barChartService;
	private ReportService reportService;
	private ReportLineChartService reportChartService;
	private String tmpReportPath;

	@FXML
	public void initialize() {
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
		progress.setVisible(true);
		info.setText(FxmlUtils.getInternalizedPropertyByKey("info.generating"));
		Task task = new Task<Void>() {
			@Override
			public Void call() throws ApplicationException, IOException {
				generateProperReport();
				return null;
			}
		};
		new Thread(task).start();
		task.exceptionProperty().addListener((observable, oldValue, newValue) ->  {
			if(newValue instanceof Exception) {
				Exception ex = (Exception) newValue;
				progress.setVisible(false);
				info.setText(FxmlUtils.getInternalizedPropertyByKey("error.default"));
				DialogUtils.errorDialog(ex.getMessage());
			}
		});
		task.setOnSucceeded(e -> {
			info.setText(FxmlUtils.getInternalizedPropertyByKey("report.info.succes"));
			progress.setVisible(false);
		});

	}

	private void generateProperReport() throws ApplicationException, IOException {
		LocalDateTime from = LocalDateTime.of(dateFrom.getValue(), timeFrom.getValue());
		LocalDateTime to = LocalDateTime.of(dateTo.getValue(), timeTo.getValue());
		final List<CommonModelFx> recordsBetween = modelsList.getRecordsBetween(from, to);
		if(modelsList.hasBoth()){
			barChartService.createHarmonicsBarCharts(recordsBetween);
			reportChartService.createLineChartsStandard(recordsBetween);
			reportChartService.createLineChartsHarmo(recordsBetween);
			List<String> userAdditionalData = getUserEnteredData();
			tmpReportPath = reportService.generateReport(recordsBetween, userAdditionalData);
		}else if(modelsList.hasOnlyHarmonics()){
			barChartService.createHarmonicsBarCharts(recordsBetween);
			reportChartService.createLineChartsHarmo(recordsBetween);
			List<String> userAdditionalData = getUserEnteredData();
			tmpReportPath = reportService.generateReportHarmo(recordsBetween, userAdditionalData);
		}else if(modelsList.hasOnlyStandard()){
			reportChartService.createLineChartsStandard(recordsBetween);
			List<String> userAdditionalData = getUserEnteredData();
			tmpReportPath = reportService.generateReportStandard(recordsBetween, userAdditionalData);
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
		progress.setVisible(true);
		info.setText(FxmlUtils.getInternalizedPropertyByKey("info.saving"));
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MS Office Documents", "*.docx"));
		File outputFile = fileChooser.showSaveDialog(vBox.getScene().getWindow());
		Task task = new Task<Void>() {
			@Override public Void call() throws IOException {
				if(outputFile==null) throw new IOException();
				SavingUtils.saveReport(tmpReportPath, outputFile);
				return null;
			}
		};
		task.exceptionProperty().addListener((observable, oldValue, newValue) ->  {
			if(newValue instanceof Exception) {
				Exception ex = (Exception) newValue;
				progress.setVisible(false);
				info.setText(FxmlUtils.getInternalizedPropertyByKey("error.saving"));
			}
		});
		task.setOnSucceeded(e -> {
			info.setText(FxmlUtils.getInternalizedPropertyByKey("report.info.saved"));
			progress.setVisible(false);
		});
		new Thread(task).start();
	}
}
