package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.models.fxmodels.ListDataFx;
import agh.inzapp.inzynierka.models.fxmodels.ListHarmoFx;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import agh.inzapp.inzynierka.models.fxmodels.TimeSpinner;
import agh.inzapp.inzynierka.services.LineChartService;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static agh.inzapp.inzynierka.utils.CommonUtils.convertToWebString;
import static agh.inzapp.inzynierka.utils.CommonUtils.getDoubleTextFormatter;
import static agh.inzapp.inzynierka.utils.FxmlUtils.restrictDatePicker;

@Controller
public class ChartViewController {
	@FXML
	private ComboBox<String> lineChartSelect;
	@FXML
	private AnchorPane apOfChart;
	@FXML
	private GridPane xGrid;
	@FXML
	private TitledPane settingsPane;
	@FXML
	private DatePicker xDateFrom, xDateTo;
	private TimeSpinner xTimeFrom, xTimeTo;
	@FXML
	private Button saveAsChartButton;
	@FXML
	private TextField chartTitle, xLabel, yLabel, yMin, yMax, yTick;
	@FXML
	private ColorPicker yColor0, yColor1, yColor2, yColor3, yColor4;
	@FXML
	private ComboBox<UniNames> yValue0, yValue1, yValue2, yValue3, yValue4;
	////////////////////////////////////
	private List<ComboBox<UniNames>> yValuesList;
	private List<ColorPicker> yColorPickerList;
	/////////////////////////////////////
	private List<DataFx> dataFxList;
	private List<HarmoFx> harmoFxList;
	private LineChartService chartService;
	private int howManyYDData;

	@FXML
	public void initialize() {
		chartService = new LineChartService();
		howManyYDData = 0;
		ListDataFx listDataFx = ListDataFx.getInstance();
		ListHarmoFx listHarmoFx = ListHarmoFx.getInstance();

		dataFxList = Objects.requireNonNull(listDataFx).getDataFxList();
		harmoFxList = Objects.requireNonNull(listHarmoFx).getHarmoFxList();
		addTimeSpinnersToGrid();
		initLists();
		bindings();
	}

	private void addTimeSpinnersToGrid() {
		xTimeFrom = new TimeSpinner(LocalTime.now());
		xTimeFrom.setId("timeSpinnerFrom");
		xTimeFrom.maxWidth(Double.MAX_VALUE);
		xGrid.add(xTimeFrom, 0, 2);
		xTimeTo = new TimeSpinner(LocalTime.now());
		xTimeTo.setId("timeSpinnerTo");
		xTimeTo.maxWidth(Double.MAX_VALUE);
		xGrid.add(xTimeTo, 1, 2);
	}

	private void initLists() {
		yValuesList = new ArrayList<>();
		yValuesList.add(yValue0);
		yValuesList.add(yValue1);
		yValuesList.add(yValue2);
		yValuesList.add(yValue3);
		yValuesList.add(yValue4);
		yColorPickerList = new ArrayList<>();
		yColorPickerList.add(yColor0);
		yColorPickerList.add(yColor1);
		yColorPickerList.add(yColor2);
		yColorPickerList.add(yColor3);
		yColorPickerList.add(yColor4);
	}

	private void bindings() {
		bindValueComboBoxes();
		bindDatePickers();
		bindSettings();
		bindYRangingTextField();
	}

	private void bindSettings() {
		settingsPane.disableProperty().bind(lineChartSelect.valueProperty().isNull());
		saveAsChartButton.disableProperty().bind(lineChartSelect.valueProperty().isNull());
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

		restrictDatePicker(xDateFrom, startDate.toLocalDate(), endDate.toLocalDate());
		restrictDatePicker(xDateTo, startDate.toLocalDate(), endDate.toLocalDate());
		xDateFrom.setValue(startDate.toLocalDate());
		xDateTo.setValue(endDate.toLocalDate());
	}

	private void bindValueComboBoxes() {
		List<UniNames> uniNamesList = List.of();
		if (isBothListPresent()) {
			uniNamesList = new ArrayList<>(dataFxList.get(0).getColumnNames());
			uniNamesList.addAll(harmoFxList.get(0).getColumnNames());
		} else if (isOnlyNormalDataPresent()) {
			uniNamesList = new ArrayList<>(dataFxList.get(0).getColumnNames());
		} else if (isOnlyHarmoDataPresent()) {
			uniNamesList = new ArrayList<>(harmoFxList.get(0).getColumnNames());
		}
		List<UniNames> finalUniNamesList = CommonUtils.deleteNonRecordsFromUniNamesList(uniNamesList);
		if (!finalUniNamesList.isEmpty()) {
			yValuesList.forEach(uniNamesComboBox -> uniNamesComboBox.setItems(FXCollections.observableArrayList(finalUniNamesList)));
		}
	}

	private void bindYRangingTextField() {
		TextFormatter<Double> minTextFormatter = getDoubleTextFormatter();
		yMin.setTextFormatter(minTextFormatter);
		TextFormatter<Double> maxTextFormatter = getDoubleTextFormatter();
		yMax.setTextFormatter(maxTextFormatter);
		TextFormatter<Double> tickTextFormatter = getDoubleTextFormatter();
		yTick.setTextFormatter(tickTextFormatter);
	}

	//PRZYCISKI "DODAJ
	@FXML
	private void yAddOnAction() {
		try {
			if (isAnyCreatedChart()) newChartOnAction();
			chartService.clearSeriesBeforeCreatingNewOne();

			List<LocalDateTime> xDataList = getFromX();

			if (CommonUtils.isSameDay(xDataList.get(0), xDataList.get(xDataList.size() - 1)))
				chartService.setXDateTickToOnlyTime();
			else
				chartService.setXDateTickToDays();

			Map<LocalDateTime, Double> xyDataMap;
			for (int i = 0; i <= howManyYDData; i++) {
				List<Double> yDataList = getFromY(i);
				if (!isSelectedValue(i)) break;
				xyDataMap = CommonUtils.zipToMap(xDataList, yDataList);
				chartService.setDefaultStyleCss("style/default_chart.css");
				chartService.createSeries(xyDataMap, yValuesList.get(i).getValue(), yColorPickerList.get(i).getValue());
			}
			setLegendColors();

		} catch (ApplicationException e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	private void setLegendColors() {
		ArrayList<Color> colors = getUserDefinedColors();
		StringBuilder style = new StringBuilder();
		for (int i = 0 ; i < colors.size() ; i++) {
			style.append("symbol-color")
					.append(i)
					.append(": ")
					.append(convertToWebString(colors.get(i)))
					.append("; ");
		}
		chartService.setStyleCssLegendColor(style.toString());
	}

	private ArrayList<Color> getUserDefinedColors() {
		ArrayList<Color> colors = new ArrayList<>();
		for (int i = 0; i <= howManyYDData; i++) {
			colors.add(yColorPickerList.get(i).getValue());
		}
		return colors;
	}

	private List<Double> getFromY(int i) throws ApplicationException {
		UniNames uniName = yValuesList.get(i).getValue();
		LocalDateTime from = LocalDateTime.of(xDateFrom.getValue(), xTimeFrom.getValue());
		LocalDateTime to = LocalDateTime.of(xDateTo.getValue(), xTimeTo.getValue());
		if (from.isBefore(to)) {
			List<Double> collect = List.of();
			if (isBothListPresent()) {
				collect = dataFxList.stream()
						.filter(model -> (model.getDate().isAfter(from) && model.getDate().isBefore(to)))
						.map(model -> model.getRecords().get(uniName))
						.collect(Collectors.toList());
				collect.addAll(harmoFxList.stream()
						.filter(model -> (model.getDate().isAfter(from) && model.getDate().isBefore(to)))
						.map(model -> model.getRecords().get(uniName))
						.collect(Collectors.toList()));
			} else if (isOnlyNormalDataPresent()) {
				collect = dataFxList.stream()
						.filter(model -> (model.getDate().isAfter(from) && model.getDate().isBefore(to)))
						.map(model -> model.getRecords().get(uniName))
						.collect(Collectors.toList());
			} else if (isOnlyHarmoDataPresent()) {
				collect = harmoFxList.stream()
						.filter(model -> (model.getDate().isAfter(from) && model.getDate().isBefore(to)))
						.map(model -> model.getRecords().get(uniName))
						.collect(Collectors.toList());
			}
			return collect.stream()
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
		}
		throw new ApplicationException("bad value"); //todo exception communicate
	}

	private List<LocalDateTime> getFromX() throws ApplicationException {
		final LocalDateTime from = LocalDateTime.of(xDateFrom.getValue(), xTimeFrom.getValue());
		final LocalDateTime to = LocalDateTime.of(xDateTo.getValue(), xTimeTo.getValue());
		if (from.isBefore(to)){
			List<LocalDateTime> collect = List.of();
			if (isBothListPresent()) {
				collect = dataFxList.stream()
						.filter(model -> (model.getDate().isAfter(from) && model.getDate().isBefore(to)))
						.map(model -> model.getDate())
						.collect(Collectors.toList());
			} else if (isOnlyNormalDataPresent()) {
				collect = dataFxList.stream()
						.filter(model -> (model.getDate().isAfter(from) && model.getDate().isBefore(to)))
						.map(model -> model.getDate())
						.collect(Collectors.toList());
			} else if (isOnlyHarmoDataPresent()) {
				collect = harmoFxList.stream()
						.filter(model -> (model.getDate().isAfter(from) && model.getDate().isBefore(to)))
						.map(model -> model.getDate())
						.collect(Collectors.toList());
			}
			return collect;
		}
		throw new ApplicationException("date out of range"); //todo exception communicate
	}
	//COMBOBOX LINE CHART + NEW LINE CHART

	@FXML
	private void switchLineChartOnAction() {
		apOfChart.getChildren().clear();
		apOfChart.getChildren().add(getCurrentSelectedLineChart());
	}

	private LineChart<String, Number> getCurrentSelectedLineChart() {
		LineChart<String, Number> currentLineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
		String selectedLineChart = lineChartSelect.getValue();
		if (selectedLineChart != null) {
			currentLineChart = chartService.getSelectedLineChart(selectedLineChart);
		}
		return currentLineChart;
	}

	@FXML
	private void newChartOnAction() {
		chartService.newLineChart();
		lineChartSelect.setItems(chartService.getLineChartsList());
		lineChartSelect.getSelectionModel().selectNext();
	}
	//PRZYCISKI "+"

	@FXML
	private void yPlusOnAction() {
		if (howManyYDData == 4) return;
		howManyYDData++;
		yValuesList.get(howManyYDData).setVisible(true);
		yColorPickerList.get(howManyYDData).setVisible(true);
	}

	@FXML
	private void yMinusOnAction() {
		if (howManyYDData == 0) return;
		yValuesList.get(howManyYDData).setVisible(false);
		yColorPickerList.get(howManyYDData).setVisible(false);
		howManyYDData--;
	}

	@FXML
	private void saveAsChartOnAction() {
		try {
			SavingUtils.saveLineChart(getCurrentSelectedLineChart());
			switchLineChartOnAction();
		} catch (Exception e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	@FXML
	private void setTitleOnKeyTyped() {
		chartService.setLineChartTitle(chartTitle.getText());
	}

	@FXML
	private void xLabelOnKeyTyped() {
		chartService.setXAxisLabel(xLabel.getText());
	}

	@FXML
	private void switchOnLegend() {
		chartService.switchCurrentLegendOn();
	}

	@FXML
	private void switchOffLegend() {
		chartService.switchCurrentLegendOff();
	}

	@FXML
	private void switchOnDataPoints() {
		chartService.switchCurrentDataPointsOn();
	}

	@FXML
	private void switchOffDataPoints() {
		chartService.switchCurrentDataPointsOff();
	}

	@FXML
	private void yLabelOnKeyTyped() {
		chartService.setYAxisLabel(yLabel.getText());
	}

	@FXML
	private void changeYRangeOnAction() {
		chartService.setAxisBounds(Double.parseDouble(yMin.getText()),
				Double.parseDouble(yMax.getText()),
				Double.parseDouble(yTick.getText()));
	}

	private boolean isSelectedValue(int i) {
		return yValuesList.get(i).getValue() != null;
	}

	private boolean isAnyCreatedChart() {
		return lineChartSelect.getSelectionModel().isEmpty();
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
