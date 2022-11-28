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
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
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
	private DatePicker xDateFrom, xDateTo;
	private TimeSpinner xTimeFrom, xTimeTo;
	@FXML
	private Button newChartButton, addToRaportButton, saveAsChartButton, yAddButton;
	@FXML
	private Button yPlusButton1, yPlusButton2, yPlusButton3, yPlusButton4;
	@FXML
	private ColorPicker yColor0 ,yColor1 ,yColor2, yColor3, yColor4;
	@FXML
	private ComboBox<UniNames> yValue0,yValue1,yValue2,yValue3,yValue4;
	////////////////////////////////////
	private List<Button> yAddValueButtonsList;
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
			dataFxList = listDataFx.getDataFxList();
			harmoFxList = listHarmoFx.getHarmoFxList();
			addTimeSpinnersToGrid();
			initLists();
			bindings();
		}
	}
	private void addTimeSpinnersToGrid() {
		xTimeFrom = new TimeSpinner();
		xTimeFrom.setId("timeSpinnerFrom");
		xGrid.add(xTimeFrom, 0,2);
		xTimeTo = new TimeSpinner();
		xTimeTo.setId("timeSpinnerTo");
		xGrid.add(xTimeTo, 1,2);
	}
	private void initLists() {
		yAddValueButtonsList = new ArrayList<>();
		yAddValueButtonsList.add(yPlusButton1);
		yAddValueButtonsList.add(yPlusButton2);
		yAddValueButtonsList.add(yPlusButton3);
		yAddValueButtonsList.add(yPlusButton4);
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
	}
	private void bindDatePickers() {
		LocalDate startDate = null;
		LocalDate endDate = null;
		if(!dataFxList.isEmpty() && !harmoFxList.isEmpty()) {
			startDate = dataFxList.stream().findFirst().get().getDate();
			endDate = dataFxList.get(dataFxList.size() - 1).getDate();
		} else if (!dataFxList.isEmpty() && harmoFxList.isEmpty()){
			startDate = dataFxList.stream().findFirst().get().getDate();
			endDate = dataFxList.get(dataFxList.size()-1).getDate();
		} else if(dataFxList.isEmpty() && !harmoFxList.isEmpty()){
			startDate = harmoFxList.stream().findFirst().get().getDate();
			endDate = harmoFxList.get(harmoFxList.size()-1).getDate();
		}
		if(startDate!=null && endDate!=null){
			restrictDatePicker(xDateFrom, startDate, endDate);
			restrictDatePicker(xDateTo, startDate, endDate);
			xDateFrom.setValue(startDate);
			xDateTo.setValue(endDate);
		}
	}
	private void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isBefore(minDate)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}else if (item.isAfter(maxDate)) {
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
		List<UniNames> uniNamesList = null;
		if(!dataFxList.isEmpty() && !harmoFxList.isEmpty()){
			uniNamesList = dataFxList.stream().findFirst().get().getColumnNames();
			uniNamesList.addAll(harmoFxList.stream().findFirst().get().getColumnHarmonicNames());
		} else if(!dataFxList.isEmpty() && harmoFxList.isEmpty()){
			uniNamesList = dataFxList.stream().findFirst().get().getColumnNames();
		} else if(dataFxList.isEmpty() && !harmoFxList.isEmpty()){
			uniNamesList = harmoFxList.stream().findFirst().get().getColumnHarmonicNames();
		}
		List<UniNames> finalUniNamesList = uniNamesList.stream().distinct().collect(Collectors.toList());
		if(!finalUniNamesList.isEmpty()){
			yValuesList.forEach(uniNamesComboBox -> uniNamesComboBox.setItems(FXCollections.observableArrayList(finalUniNamesList)));
		}
	}

	//PRZYCISKI "DODAJ
	@FXML
	private void yAddOnAction() {
		try {
			List<LocalDateTime> xDataList = getFromX();
			Map<LocalDateTime, Double> xyDataMap;
			for(int i = 0; i < howManyYDData; i++){
				List<Double> yDataList = getFromY(i);
				xyDataMap = FxmlUtils.zipToMap(xDataList, yDataList);
				chartService.addSeriesToChart(xyDataMap);
				chartService.setSeriesName(yValuesList.get(i).getValue());
				chartService.setSeriesColor(yColorPickerList.get(i).getValue());
			}
		} catch (ApplicationException e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	private List<Double> getFromY(int i) {
		final UniNames value = yValuesList.get(i).getValue();
		return null;
	}

	private List<LocalDateTime> getFromX() throws ApplicationException {
		final LocalDateTime from = LocalDateTime.of(xDateFrom.getValue(), xTimeFrom.getValue());
		final LocalDateTime to = LocalDateTime.of(xDateTo.getValue(), xTimeTo.getValue());
		if(to.isAfter(from)){
			List<? extends CommonDbModel> queryListNormal = DataManager.findAllNormalByDateBetweenAndTimeBetween(from.toLocalDate(), to.toLocalDate(), from.toLocalTime(), to.toLocalTime());
			List<? extends CommonDbModel> queryListHarmo = DataManager.findAllHarmoByDateBetweenAndTimeBetween(from.toLocalDate(), to.toLocalDate(), from.toLocalTime(), to.toLocalTime());
			List<LocalDateTime> timeRecordList = queryListNormal.stream().map(e -> LocalDateTime.of(e.getDate(), e.getTime())).collect(Collectors.toList());
			timeRecordList.addAll(queryListHarmo.stream().map(e -> LocalDateTime.of(e.getDate(), e.getTime())).collect(Collectors.toList()));
			return timeRecordList.stream().distinct().collect(Collectors.toList());
		}
		throw new ApplicationException("date out of range"); //todo exception comunicat
	}

	//COMBOBOX LINE CHART + NEW LINECHART
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
	private void yPlusOnAction1() {
		if(yPlusButton2.isVisible()) return;
		if(yPlusButton1.getText().equals("+")){
			howManyYDData++;
			yPlusButton1.setText("-");
			yPlusButton1.setVisible(false); //wylaczenie kliknietego
			yPlusButton2.setVisible(true); //wlaczenie nastepnego
			//wlaczenie kolejnych
			yValue1.setVisible(true);
			yColor1.setVisible(true);
		}else{
			howManyYDData--;
			yPlusButton1.setText("+");
			yPlusButton2.setVisible(false); //wlaczenie poprzedniego
			//wylaczenie w kliknietym wierszu
			yValue1.setVisible(false);
			yColor1.setVisible(false);
		}
	}
	@FXML
	private void yPlusOnAction2() {
		if(yPlusButton3.isVisible()) return;
		if(yPlusButton2.getText().equals("+")){
			howManyYDData++;
			yPlusButton2.setText("-");
			yPlusButton2.setVisible(false);
			yPlusButton3.setVisible(true);
			yValue2.setVisible(true);
			yColor2.setVisible(true);
		}else{
			howManyYDData--;
			yPlusButton2.setText("+");
			yPlusButton2.setVisible(false);
			yValue2.setVisible(false);
			yColor2.setVisible(false);
			yPlusButton1.setVisible(true);
		}
	}
	@FXML
	private void yPlusOnAction3() {
		if(yPlusButton4.isVisible()) return;
		if(yPlusButton3.getText().equals("+")){
			howManyYDData++;
			yPlusButton3.setText("-");
			yPlusButton3.setVisible(false);
			yPlusButton4.setVisible(true);
			yValue3.setVisible(true);
			yColor3.setVisible(true);
		}else{
			howManyYDData--;
			yPlusButton3.setText("+");
			yPlusButton2.setVisible(true);
			yValue3.setVisible(false);
			yColor3.setVisible(false);
			yPlusButton3.setVisible(false);
		}
	}
	@FXML
	private void yPlusOnAction4() {
		if(yPlusButton4.getText().equals("+")){
			howManyYDData++;
			yPlusButton4.setText("-");
			yValue4.setVisible(true);
			yColor4.setVisible(true);
		}else{
			howManyYDData--;
			yPlusButton4.setText("+");
			yValue4.setVisible(false);
			yColor4.setVisible(false);
			yPlusButton4.setVisible(false);
			yPlusButton3.setVisible(true);
		}
	}

	//SAVE TO RAPORT
	@FXML
	private void addChartToRaportOnAction() {
	}
	@FXML
	private void saveAsChartOnAction() {
	}
}
