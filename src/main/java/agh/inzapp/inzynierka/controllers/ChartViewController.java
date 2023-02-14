package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.ListCommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.TimeSpinner;
import agh.inzapp.inzynierka.directors.UserChartDirector;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.GridPaneUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
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
	////////////////////////////////////
	private List<ComboBox<UniNames>> yValuesList;
	private List<ColorPicker> yColorPickerList;
	private List<Button> deletingButtons;
	/////////////////////////////////////
	private ListCommonModelFx modelsList;
	private UserChartDirector chartsDirector;

	public void initialize() {
		try {
			modelsList = ListCommonModelFx.getInstance();
			chartsDirector = new UserChartDirector();
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
		deletingButtons = new ArrayList<>();
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
		if (modelsBetweenSelectedTime.get(0).getHarmonics().containsKey(uniName)) {
			yData = modelsBetweenSelectedTime.stream()
					.map(model -> model.getHarmonics().get(uniName))
					.filter(Objects::nonNull)
					.toList();
		} else if (modelsBetweenSelectedTime.get(0).getRecords().containsKey(uniName)) {
			yData = modelsBetweenSelectedTime.stream()
					.map(model -> model.getRecords().get(uniName))
					.filter(Objects::nonNull)
					.toList();
		}
		xyDataMap = CommonUtils.zipToMap(xData, yData);
		return xyDataMap;
	}

	@FXML
	private void newChart() {
		chartsDirector.newLineChart();
		chartCombo.setItems(chartsDirector.getLineChartsList());
		chartCombo.getSelectionModel().selectLast();
		setSettingsPane();
		yValuesList.forEach(value -> value.getSelectionModel().clearSelection());
	}

	@FXML
	private void deleteChart() {
		if (chartCombo.getItems().size() > 1 && chartCombo.getSelectionModel().getSelectedItem() != null) {
			chartsDirector.deleteChart(chartCombo.getValue());
			chartCombo.getItems().remove(chartCombo.getValue());
			chartCombo.setItems(chartsDirector.getLineChartsList());
			chartCombo.getSelectionModel().selectFirst();
			chartsDirector.getSelectedLineChart(chartCombo.getValue());
			repaintChart();
		}
	}

	@FXML
	private void switchLineChart(ActionEvent event) {
		repaintChart();
		setSettings();
	}

	@FXML
	private void saveAsChartOnAction() {
		try {
			final LineChart<Number, Number> currentSelectedLineChart = getCurrentSelectedLineChart();
			if (currentSelectedLineChart != null) {
				SavingUtils.saveLineChart(currentSelectedLineChart);
				repaintChart();
				setSettings();
			}
		} catch (Exception e) {
			DialogUtils.errorDialog(e.getMessage());
		}
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
		final boolean b = yValuesList.stream().noneMatch(value -> value.getSelectionModel().isEmpty());
		if(!b) return;
		try {
			chartsDirector.clearSeriesBeforeCreatingNewOne();
			List<CommonModelFx> modelsInSelectedTime = getRecordsBetweenSelectedTime();

			if (isTheSameDay()) chartsDirector.setXDateTickToOnlyTime();
			else chartsDirector.setXDateTickToDays();

			ArrayList<UniNames> yToSave = new ArrayList<>();
			ArrayList<Color> yColorsToSave = new ArrayList<>();
			for (int i = 0; i < yValuesList.size(); i++) {
				if (!isSelectedValue(i)) break;
				Map<LocalDateTime, Double> xyDataMap = getSeriesDataMap(modelsInSelectedTime, i);
				final UniNames name = yValuesList.get(i).getValue();
				final Color color = yColorPickerList.get(i).getValue();
				chartsDirector.createSeries(xyDataMap, name, color);
				yToSave.add(name);
				yColorsToSave.add(color);
			}
			chartsDirector.saveCurrentSettings(yToSave, yColorsToSave);
			setLegendColors();
			setSettingsPane();
		} catch (ApplicationException e) {
			DialogUtils.errorDialog(e.getMessage());
		}

	}

	@FXML // "Dodaj" dodaje linie w grid y
	private void addLine() {
		int row = yGrid.getRowCount();
			final ComboBox<UniNames> newComboBox = getUniNamesComboBox();
			final ColorPicker newColorPicker = getColorPicker();
			final Button newDelete = createButton(row + 1, newComboBox, newColorPicker);
			yGrid.addRow(row, newComboBox, newColorPicker, newDelete);
	}

	private ColorPicker getColorPicker() {
		final ColorPicker newColorPicker = new ColorPicker();
		Random obj = new Random();
		int rand_num = obj.nextInt(0xffffff + 1);
		String colorCode = String.format("#%06x", rand_num);
		newColorPicker.setValue(Color.web(colorCode));
		newColorPicker.setOnAction(e -> setCurrentChart());
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

	private Button createButton(int row, ComboBox<UniNames> newComboBox, ColorPicker newColorPicker) {
		Button button = new Button();
		Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sign_x.png")));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(17);
		imageView.setFitWidth(17);
		button.setGraphic(imageView);
		button.setOnAction(event -> {
			GridPaneUtils.removeRow(yGrid, GridPane.getRowIndex(button));
			yColorPickerList.remove(newColorPicker);//delete from list color picker
			yValuesList.remove(newComboBox);//delete from list color picker
			deletingButtons.remove(button);
			setCurrentChart();
		});
		deletingButtons.add(button);
		return button;
	}

	@FXML
	private void setTitle(KeyEvent keyEvent) {
		if (keyEvent.getCode().equals(KeyCode.ENTER)) {
			chartsDirector.setLineChartTitle(chartTitle.getText());
			repaintChart();
		}
	}

	@FXML
	private void setXLabel(KeyEvent keyEvent) {
		if (keyEvent.getCode().equals(KeyCode.ENTER)) {
			chartsDirector.setXAxisLabel(xLabel.getText());
			repaintChart();
		}
	}

	@FXML
	private void setYLabel(KeyEvent keyEvent) {
		if (keyEvent.getCode().equals(KeyCode.ENTER)) {
			chartsDirector.setYAxisLabel(yLabel.getText());
			repaintChart();
		}
	}

	@FXML
	private void setYMin(KeyEvent keyEvent) {
		if (keyEvent.getCode().equals(KeyCode.ENTER)) {
			chartsDirector.setYMin(Double.parseDouble(yMin.getText()));
			repaintChart();
		}
	}

	@FXML
	private void setYMax(KeyEvent keyEvent) {
		if (keyEvent.getCode().equals(KeyCode.ENTER)) {
			chartsDirector.setYMax(Double.parseDouble(yMax.getText()));
			repaintChart();
		}
	}

	@FXML
	private void setYTick(KeyEvent keyEvent) {
		if (keyEvent.getCode().equals(KeyCode.ENTER)) {
			chartsDirector.setYTick(Double.parseDouble(yTick.getText()));
			repaintChart();
		}
	}

	@FXML
	private void switchOnLegend() {
		chartsDirector.switchCurrentLegendOn();
		repaintChart();
	}

	@FXML
	private void switchOffLegend() {
		chartsDirector.switchCurrentLegendOff();
		repaintChart();
	}

	@FXML
	private void switchOnDataPoints() {
		chartsDirector.switchCurrentDataPointsOn();
		repaintChart();
	}

	@FXML
	private void switchOffDataPoints() {
		chartsDirector.switchCurrentDataPointsOff();
		repaintChart();
	}

	private void setSettingsPane(){
		final List<Object> chartSettings = chartsDirector.getChartSettingsPane();
		if (chartSettings.isEmpty()) {
			chartTitle.setText("");
			xLabel.setText("");
			yLabel.setText("");
			legendOff.setSelected(true);
			dataOff.setSelected(true);
			yMin.setText("");
			yMax.setText("");
			yTick.setText("");
			return;
		}
		chartTitle.setText((String) chartSettings.get(0));
		xLabel.setText((String) chartSettings.get(1));
		yLabel.setText((String) chartSettings.get(2));

		if ((boolean) chartSettings.get(3)) legendOn.setSelected(true);
		else legendOff.setSelected(true);
		if ((boolean) chartSettings.get(4)) dataOn.setSelected(true);
		else dataOff.setSelected(true);

		yMin.setText(String.valueOf((double) chartSettings.get(5)));
		yMax.setText(String.valueOf((double) chartSettings.get(6)));
		yTick.setText(String.valueOf((double) chartSettings.get(7)));
	}

	private void setSettings() {
		setSettingsPane();
		final List<Object> chartSettings = chartsDirector.getChartYDataSettings();
		if (!chartSettings.isEmpty()) {
			List<UniNames> savedValues = new ArrayList<>((List<UniNames>) chartSettings.get(0));
			List<Color> savedColors = new ArrayList<>((List<Color>) chartSettings.get(1));
			if (savedValues.isEmpty() && savedColors.isEmpty()){
				yValuesList.forEach(value -> value.getSelectionModel().clearSelection());
				return;
			}
			if (yValuesList.size() <= savedValues.size()) {
				for(int i = yValuesList.size() ; i < savedValues.size();i++){
					addLine();
				}
			}else if (deletingButtons.size() > savedValues.size()-1) {
				for(int i = deletingButtons.size(); i > savedValues.size()-1;i--){
					deletingButtons.get(i-1).fire();
				}
			}
			final Iterator<ComboBox<UniNames>> comboBoxIterator = yValuesList.iterator();
			final Iterator<ColorPicker> colorPickerIterator = yColorPickerList.iterator();
			final Iterator<UniNames> namesI = savedValues.iterator();
			final Iterator<Color> colorIterator = savedColors.iterator();
			while (comboBoxIterator.hasNext() && namesI.hasNext()) {
				comboBoxIterator.next().setValue(namesI.next());
				colorPickerIterator.next().setValue(colorIterator.next());
			}
		}
	}

	private List<CommonModelFx> getRecordsBetweenSelectedTime() throws ApplicationException {
		final LocalDateTime from = LocalDateTime.of(xDateFrom.getValue(), xTimeFrom.getValue());
		final LocalDateTime to = LocalDateTime.of(xDateTo.getValue(), xTimeTo.getValue());
		return modelsList.getRecordsBetween(from, to);
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
		for (int i = 0; i < colors.size(); i++) {
			style.append("symbol-color")
					.append(i)
					.append(": ")
					.append(convertToWebString(colors.get(i)))
					.append("; ");
		}
		chartsDirector.setStyleCssLegendColor(style.toString());
	}

	private ArrayList<Color> getUserDefinedColors() {
		ArrayList<Color> colors = new ArrayList<>();
		for (ColorPicker colorPicker : yColorPickerList) {
			colors.add(colorPicker.getValue());
		}
		return colors;
	}

	private void repaintChart() {
		final LineChart<Number, Number> currentSelectedLineChart = getCurrentSelectedLineChart();
		if (currentSelectedLineChart != null) {
			apOfChart.getChildren().clear();
			apOfChart.getChildren().add(currentSelectedLineChart);
		}
	}

	private LineChart<Number, Number> getCurrentSelectedLineChart() {
		String selectedLineChart = chartCombo.getValue();
		if (selectedLineChart != null)
			return chartsDirector.getSelectedLineChart(selectedLineChart);
		else
			return null;
	}

	//todo zapisywanie ustawień wszystkich pomiędzy przełączaniem idk czy to zrobić

}
