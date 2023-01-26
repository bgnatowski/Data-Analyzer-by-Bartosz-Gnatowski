package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.ListCommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.TimeSpinner;
import agh.inzapp.inzynierka.services.UserChartService;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.GridPaneUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.*;

import static agh.inzapp.inzynierka.utils.CommonUtils.convertToWebString;
import static agh.inzapp.inzynierka.utils.CommonUtils.getDoubleTextFormatter;
import static agh.inzapp.inzynierka.utils.FxmlUtils.restrictDatePicker;

@Controller
public class ChartViewController {
	@FXML
	private ComboBox<String> chartCombo;
	@FXML
	private AnchorPane apOfChart, apSettings;
	@FXML
	private GridPane xGrid, yGrid;
	@FXML
	private TitledPane settingsPane, yPane, xPane;
	@FXML
	private DatePicker xDateFrom, xDateTo;
	private TimeSpinner xTimeFrom, xTimeTo;
	@FXML
	private RadioButton legendOn, legendOff, dataOn, dataOff;
	@FXML
	private Button saveAsChartButton, yButton0, buttonAddLine, newChartButton, deleteChartButton;
	@FXML
	private TextField chartTitle, xLabel, yLabel, yMin, yMax, yTick;
	@FXML
	private ColorPicker yColor0;
	@FXML
	private ComboBox<UniNames> yValue0;
	public ToggleGroup legendButtonGroup, dataButtonsGroup;
	////////////////////////////////////
	private List<ComboBox<UniNames>> yValuesList;
	private List<ColorPicker> yColorPickerList;
	/////////////////////////////////////
	private ListCommonModelFx modelsList;
	private UserChartService chartService;
	private int howManyYDData;

	public void initialize() {
		try {
			modelsList = ListCommonModelFx.getInstance();
			chartService = new UserChartService();
			howManyYDData = 0;
			addTimeSpinnersToGrid();
			initLists();
			bindings();
		} catch (ApplicationException e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	private void addTimeSpinnersToGrid() {
		xTimeFrom = new TimeSpinner();
		xTimeFrom.setId("timeSpinnerFrom");
		xTimeFrom.maxWidth(Double.MAX_VALUE);
		xTimeFrom.setOnMouseClicked(event -> setCurrentChart());
		xTimeTo = new TimeSpinner();
		xTimeTo.setId("timeSpinnerTo");
		xTimeTo.maxWidth(Double.MAX_VALUE);
		xTimeTo.setOnMouseClicked(event -> setCurrentChart());
		xGrid.add(xTimeFrom, 0, 2);
		xGrid.add(xTimeTo, 1, 2);
	}

	private void initLists() {
		yValuesList = new ArrayList<>();
		yValuesList.add(yValue0);
		yColorPickerList = new ArrayList<>();
		yColorPickerList.add(yColor0);
	}
	private void bindings() {
		bindValueComboBoxes();
		bindDatePickers();
		bindSettings();
		bindYRangingTextField();
	}
	private void bindSettings() {
		settingsPane.setExpanded(false);
		settingsPane.disableProperty().bind(chartCombo.valueProperty().isNull());
		xPane.disableProperty().bind(chartCombo.valueProperty().isNull());
		yPane.disableProperty().bind(chartCombo.valueProperty().isNull());
		saveAsChartButton.disableProperty().bind(chartCombo.valueProperty().isNull());

	}
	private void bindDatePickers() {
		LocalDateTime startDate = modelsList.getStartDate();
		LocalDateTime endDate = modelsList.getEndDate();
		restrictDatePicker(xDateFrom, startDate.toLocalDate(), endDate.toLocalDate());
		restrictDatePicker(xDateTo, startDate.toLocalDate(), endDate.toLocalDate());
		xDateFrom.setValue(startDate.toLocalDate());
		xDateTo.setValue(endDate.toLocalDate());
	}
	private void bindValueComboBoxes() {
		List<UniNames> uniNamesList = modelsList.getColumnNames();
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

	private Map<LocalDateTime, Double> getSeriesDataMap(List<CommonModelFx> modelsBetweenSelectedTime, int i) throws ApplicationException {
		Map<LocalDateTime, Double> xyDataMap;
		UniNames uniName = yValuesList.get(i).getValue();
		List<LocalDateTime> xData = modelsBetweenSelectedTime.stream().map(CommonModelFx::getDate).toList();
		List<Double> yData = new ArrayList<>();
		if(modelsBetweenSelectedTime.get(0).getHarmonics().containsKey(uniName)){
			yData = modelsBetweenSelectedTime.stream()
					.map(model -> model.getHarmonics().get(uniName))
					.filter(Objects::nonNull)
					.toList();
		}else if(modelsBetweenSelectedTime.get(0).getRecords().containsKey(uniName)){
			yData = modelsBetweenSelectedTime.stream()
					.map(model -> model.getRecords().get(uniName))
					.filter(Objects::nonNull)
					.toList();
		}
		xyDataMap = CommonUtils.zipToMap(xData, yData);
		return xyDataMap;
	}

	@FXML
	private void switchLineChart() {
		repaintChart();
		setSettings();
	}
	@FXML
	private void newChart() {
		chartService.newLineChart();
		chartCombo.setItems(chartService.getLineChartsList());
		chartCombo.getSelectionModel().selectLast();
	}

	@FXML
	private void deleteChart(ActionEvent actionEvent) {
		if(!chartCombo.getSelectionModel().isEmpty()){
			chartCombo.getItems().remove(chartCombo.getValue());
			chartService.deleteChart(chartCombo.getValue());

			chartCombo.setItems(chartService.getLineChartsList());
			chartCombo.getSelectionModel().selectPrevious();
			chartService.getSelectedLineChart(chartCombo.getValue());
			repaintChart();
		}
	}
	@FXML
	private void saveAsChartOnAction() {
		try {
			SavingUtils.saveLineChart(getCurrentSelectedLineChart());
			switchLineChart();
		} catch (Exception e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	private List<CommonModelFx> getRecordsBetweenSelectedTime() throws ApplicationException {
		final LocalDateTime from = LocalDateTime.of(xDateFrom.getValue(), xTimeFrom.getValue());
		final LocalDateTime to = LocalDateTime.of(xDateTo.getValue(), xTimeTo.getValue());
		return modelsList.getRecordsBetween(from, to);
	}

	@FXML
	private void setXStart(ActionEvent actionEvent) {
		setCurrentChart();
	}

	@FXML
	private void setXEnd(ActionEvent actionEvent) {
		setCurrentChart();
	}

	@FXML
	private void setValue(ActionEvent actionEvent) {
		setCurrentChart();
	}

	@FXML
	private void setColor(ActionEvent actionEvent) {
		setCurrentChart();
	}

	private void setCurrentChart() {
		System.out.println("setCurrentChart()");
		try {
			chartService.clearSeriesBeforeCreatingNewOne();
			List<CommonModelFx> modelsInSelectedTime = getRecordsBetweenSelectedTime();
			
			if(isTheSameDay()) chartService.setXDateTickToOnlyTime();
			else chartService.setXDateTickToDays();

			for (int i = 0; i < yValuesList.size(); i++) {
				if (!isSelectedValue(i)) break;
				System.out.println("Iteruje po yValueList: " + i + "/"+yValuesList.size());
				Map<LocalDateTime, Double> xyDataMap = getSeriesDataMap(modelsInSelectedTime, i);
				chartService.createSeries(xyDataMap, yValuesList.get(i).getValue(), yColorPickerList.get(i).getValue());
			}
			setLegendColors();
			setSettings();
		} catch (ApplicationException e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	@FXML // "Dodaj" dodaje linie w Ygrid
	private void addLine(ActionEvent actionEvent) {
		int row = yGrid.getRowCount();
		if(row<6){
			final ComboBox<UniNames> newComboBox = getUniNamesComboBox();
			final ColorPicker newColorPicker = getColorPicker();
			final Button newDelete = createButton(row+1, newComboBox, newColorPicker);
			yGrid.addRow(row, newComboBox, newColorPicker, newDelete);
		}
	}

	private ColorPicker getColorPicker() {
		final ColorPicker newColorPicker = new ColorPicker();
		Random obj = new Random();
		int rand_num = obj.nextInt(0xffffff + 1);
		String colorCode = String.format("#%06x", rand_num);
		newColorPicker.setValue(Color.web(colorCode));
		newColorPicker.setOnAction(e->setCurrentChart());
		yColorPickerList.add(newColorPicker);
		return newColorPicker;
	}

	private ComboBox<UniNames> getUniNamesComboBox() {
		final ComboBox<UniNames> newComboBox = new ComboBox<>();
		newComboBox.setMaxWidth(Double.MAX_VALUE);
		newComboBox.setOnAction(event -> setCurrentChart());
		yValuesList.add(newComboBox);
		bindValueComboBoxes();
		return newComboBox;
	}

	private Button createButton(int row, ComboBox<UniNames> newComboBox, ColorPicker newColorPicker){
		Button button = new Button();
		Image image = new Image(getClass().getResource("/images/sign_x.png").toString());
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(17);
		imageView.setFitWidth(17);
		button.setGraphic(imageView);
		button.setOnAction(event -> {
			GridPaneUtils.removeRow(yGrid, GridPane.getRowIndex(button));
			yColorPickerList.remove(newColorPicker);//delete from list colorpicker
			yValuesList.remove(newComboBox);//delete from list colorpicker
			setCurrentChart();
		});
		return button;
	}

	@FXML
	private void setTitle(KeyEvent keyEvent) {
		if(keyEvent.getCode().equals(KeyCode.ENTER)){
			chartService.setLineChartTitle(chartTitle.getText());
			repaintChart();
		}
	}
	@FXML
	private void setXLabel(KeyEvent keyEvent) {
		if(keyEvent.getCode().equals(KeyCode.ENTER)){
			chartService.setXAxisLabel(xLabel.getText());
			repaintChart();
		}
	}
	@FXML
	private void setYLabel(KeyEvent keyEvent) {
		if(keyEvent.getCode().equals(KeyCode.ENTER)){
			chartService.setYAxisLabel(yLabel.getText());
			repaintChart();
		}
	}
	@FXML
	private void setYMin(KeyEvent keyEvent) {
		if(keyEvent.getCode().equals(KeyCode.ENTER)){
			chartService.setYMin(Double.parseDouble(yMin.getText()));
			repaintChart();
		}
	}
	@FXML
	private void setYMax(KeyEvent keyEvent) {
		if(keyEvent.getCode().equals(KeyCode.ENTER)){
			chartService.setYMax(Double.parseDouble(yMax.getText()));
			repaintChart();
		}
	}
	@FXML
	private void setYTick(KeyEvent keyEvent) {
		if(keyEvent.getCode().equals(KeyCode.ENTER)){
			chartService.setYTick(Double.parseDouble(yTick.getText()));
			repaintChart();
		}
	}
	@FXML
	private void switchOnLegend() {
		chartService.switchCurrentLegendOn();
		repaintChart();
	}
	@FXML
	private void switchOffLegend() {
		chartService.switchCurrentLegendOff();
		repaintChart();
	}
	@FXML
	private void switchOnDataPoints() {
		chartService.switchCurrentDataPointsOn();
		repaintChart();
	}
	@FXML
	private void switchOffDataPoints() {
		chartService.switchCurrentDataPointsOff();
		repaintChart();
	}

	private void setSettings() {
		final List<Object> chartSettings = chartService.getChartSettings();
		if (chartSettings.isEmpty()){
			chartTitle.setText("");
			xLabel.setText("");
			yLabel.setText("");
			legendOff.setSelected(true);
			dataOff.setSelected(true);
			yMin.setText("");
			yMax.setText("");
			yTick.setText("");
		}
		chartTitle.setText((String)chartSettings.get(0));
		xLabel.setText((String)chartSettings.get(1));
		yLabel.setText((String)chartSettings.get(2));

		if((boolean) chartSettings.get(3)) legendOn.setSelected(true);
		else legendOff.setSelected(true);
		if((boolean) chartSettings.get(4)) dataOn.setSelected(true);
		else dataOff.setSelected(true);

		yMin.setText(String.valueOf((double) chartSettings.get(5)));
		yMax.setText(String.valueOf((double) chartSettings.get(6)));
		yTick.setText(String.valueOf((double) chartSettings.get(7)));
	}
	private boolean isSelectedValue(int i) {
		return yValuesList.get(i).getValue() != null;
	}

	private boolean isTheSameDay() {
		final LocalDateTime from = LocalDateTime.of(xDateFrom.getValue(), xTimeFrom.getValue());
		final LocalDateTime to = LocalDateTime.of(xDateTo.getValue(), xTimeTo.getValue());
		return CommonUtils.isSameDay(from, to);
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

	private void repaintChart() {
		apOfChart.getChildren().clear();
		apOfChart.getChildren().add(getCurrentSelectedLineChart());
	}

	private LineChart<Number, Number> getCurrentSelectedLineChart() {
		String selectedLineChart = chartCombo.getValue();
		if (selectedLineChart != null)
			return chartService.getSelectedLineChart(selectedLineChart);
		else
			return new LineChart<>(new NumberAxis(), new NumberAxis());
	}


	//todo usuwanie wykresu
	//todo zapisywanie ustawien wszysktich poiedzy przelaczaniem

}
