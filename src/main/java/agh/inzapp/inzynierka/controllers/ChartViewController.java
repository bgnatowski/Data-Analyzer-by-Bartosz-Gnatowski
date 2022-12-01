package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.models.ListDataFx;
import agh.inzapp.inzynierka.models.ListHarmoFx;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import agh.inzapp.inzynierka.models.fxmodels.TimeSpinner;
import agh.inzapp.inzynierka.services.ChartService;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
//	@FXML
//	private Button newChartButton, addToRaportButton, saveAsChartButton, yAddButton, yPlusButton, yMinusButton;
	@FXML
	private TextField chartTitle, xLabel, yLabel;
	@FXML
	private ColorPicker yColor0 ,yColor1 ,yColor2, yColor3, yColor4;
	@FXML
	private ComboBox<UniNames> yValue0,yValue1,yValue2,yValue3,yValue4;
	////////////////////////////////////
	private List<ComboBox<UniNames>> yValuesList;
	private List<ColorPicker> yColorPickerList;
	/////////////////////////////////////
	private List<DataFx> dataFxList;
	private List<HarmoFx> harmoFxList;
	private ChartService chartService;
	private int howManyYDData;
	@FXML
	public void initialize(){
		chartService = new ChartService();
		howManyYDData = 0;
		ListDataFx listDataFx = ListDataFx.getInstance();
		ListHarmoFx listHarmoFx = ListHarmoFx.getInstance();
		if(listDataFx!= null || listHarmoFx!=null){
			dataFxList = Objects.requireNonNull(listDataFx).getDataFxList();
			harmoFxList = Objects.requireNonNull(listHarmoFx).getHarmoFxList();
			addTimeSpinnersToGrid();
			initLists();
			bindings();
		}
	}
	private void addTimeSpinnersToGrid() {
		xTimeFrom = new TimeSpinner();
		xTimeFrom.setId("timeSpinnerFrom");
		xTimeFrom.maxWidth(Double.MAX_VALUE);
		xGrid.add(xTimeFrom, 0,2);
		xTimeTo = new TimeSpinner();
		xTimeTo.setId("timeSpinnerTo");
		xTimeTo.maxWidth(Double.MAX_VALUE);
		xGrid.add(xTimeTo, 1,2);
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
	}

	private void bindSettings() {
		settingsPane.disableProperty().bind(lineChartSelect.valueProperty().isNull());
	}

	private void bindDatePickers() {
		LocalDateTime startDate = LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now();
		if(isBothListPresent()){
			startDate = dataFxList.get(0).getDate();
			endDate = dataFxList.get(dataFxList.size()-1).getDate();
		}
		else if (isOnlyNormalDataPresent()){
			startDate = dataFxList.get(0).getDate();
			endDate = dataFxList.get(dataFxList.size()-1).getDate();
		} else if(isOnlyHarmoDataPresent()){
			startDate = harmoFxList.get(0).getDate();
			endDate = harmoFxList.get(harmoFxList.size()-1).getDate();
		}

		restrictDatePicker(xDateFrom, startDate.toLocalDate(), endDate.toLocalDate());
		restrictDatePicker(xDateTo, startDate.toLocalDate(), endDate.toLocalDate());
		xDateFrom.setValue(startDate.toLocalDate());
		xDateTo.setValue(endDate.toLocalDate());
	}

	private void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isBefore(minDate)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						} else if (item.isAfter(maxDate)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}
					}
				};
			}
		};
		datePicker.setDayCellFactory(dayCellFactory);
	}

	private void bindValueComboBoxes() {
		List<UniNames> uniNamesList = new ArrayList<>();
		if(isBothListPresent()){
			uniNamesList = dataFxList.get(0).getColumnNames();
			uniNamesList.addAll(harmoFxList.get(0).getColumnNames());
		} else if(isOnlyNormalDataPresent()){
			uniNamesList = dataFxList.get(0).getColumnNames();
		} else if(isOnlyHarmoDataPresent()){
			uniNamesList = harmoFxList.get(0).getColumnNames();
		}
		List<UniNames> finalUniNamesList = CommonUtils.deleteNonRecordsFromUniNamesList(uniNamesList);
		if(!finalUniNamesList.isEmpty()){
			yValuesList.forEach(uniNamesComboBox -> uniNamesComboBox.setItems(FXCollections.observableArrayList(finalUniNamesList)));
		}
	}
	//PRZYCISKI "DODAJ

	@FXML
	private void yAddOnAction() {
		try {
			if(isAnyCreatedChart()) newChartOnAction();
			chartService.clearSeriesBeforeCreatingNewOne();

			List<LocalDateTime> xDataList = getFromX();

			if(CommonUtils.isSameDay(xDataList.get(0), xDataList.get(xDataList.size()-1)))
				chartService.setXDateTickToOnlyTime();
			else
				chartService.setXDateTickToDays();

			Map<LocalDateTime, Double> xyDataMap;
			for(int i = 0; i <= howManyYDData; i++){
				List<Double> yDataList = getFromY(i);
				if (!isSelectedValue(i)) break;
				xyDataMap = CommonUtils.zipToMap(xDataList, yDataList);
				chartService.createSeries(xyDataMap, yValuesList.get(i).getValue(), yColorPickerList.get(i).getValue());
			}
		} catch (ApplicationException e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}
	private List<Double> getFromY(int i) throws ApplicationException {
		UniNames uniName = yValuesList.get(i).getValue();
		LocalDateTime from = LocalDateTime.of(xDateFrom.getValue(), xTimeFrom.getValue());
		LocalDateTime to = LocalDateTime.of(xDateTo.getValue(), xTimeTo.getValue());
		if(from.isBefore(to)){
			List<Long> allIdByDateBetween = DataManager.findIdByDateBetween(from, to);
			List<CommonDbModel> normalByIdBetween = DataManager.findAllNormalByIdBetween(allIdByDateBetween.get(0), allIdByDateBetween.get(allIdByDateBetween.size() - 1));
			List<CommonDbModel> harmoByIdBetween = DataManager.findAllHarmoByIdBetween(allIdByDateBetween.get(0), allIdByDateBetween.get(allIdByDateBetween.size() - 1));
			List<Double> valuesList = new ArrayList<>();
			valuesList.addAll(normalByIdBetween.stream().map(record -> record.getRecords().get(uniName))
					.filter(Objects::nonNull)
					.collect(Collectors.toList()));
			valuesList.addAll(harmoByIdBetween.stream().map(record -> record.getRecords().get(uniName))
					.filter(Objects::nonNull)
					.collect(Collectors.toList()));
			return valuesList;
		}
		throw new ApplicationException("bad value"); //todo exception communicate
	}

	private List<LocalDateTime> getFromX() throws ApplicationException {
		final LocalDateTime from = LocalDateTime.of(xDateFrom.getValue(), xTimeFrom.getValue());
		final LocalDateTime to = LocalDateTime.of(xDateTo.getValue(), xTimeTo.getValue());
		if(from.isBefore(to))
			return DataManager.findTimeSeriesByLocalDateTimeBetween(from, to);
		throw new ApplicationException("date out of range"); //todo exception communicate
	}

	//COMBOBOX LINE CHART + NEW LINE CHART

	@FXML
	private void switchLineChartOnAction() {
		LineChart<String, Number> currentLineChart;
		String selectedLineChart = lineChartSelect.getValue();
		if(selectedLineChart!=null){
			currentLineChart = chartService.getSelectedLineChart(selectedLineChart);
			apOfChart.getChildren().clear();
			apOfChart.getChildren().add(currentLineChart);
		}
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
		if(howManyYDData==4) return;
		howManyYDData++;
		yValuesList.get(howManyYDData).setVisible(true);
		yColorPickerList.get(howManyYDData).setVisible(true);
	}
	@FXML
	private void yMinusOnAction() {
		if(howManyYDData==0) return;
		yValuesList.get(howManyYDData).setVisible(false);
		yColorPickerList.get(howManyYDData).setVisible(false);
		howManyYDData--;
	}

	//SAVE TO RAPORT

	@FXML
	private void addChartToRaportOnAction() {
	}
	@FXML
	private void saveAsChartOnAction() {
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
	private void yLabelOnKeyTyped() {
		chartService.setYAxisLabel(yLabel.getText());
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
