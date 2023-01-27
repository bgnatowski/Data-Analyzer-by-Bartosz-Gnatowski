package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.builders.LineChartBuilder;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserChartService {
	private final List<LineChartBuilder> buildersList;
	private LineChart<Number, Number> currentLineChart;
	private LineChartBuilder builder;

	public UserChartService() {
		buildersList = new ArrayList<>();
	}
	public ObservableList<String> getLineChartsList() {
		List<String> chartList = new ArrayList<>();
		for(int i = 0; i < buildersList.size(); i++){
			final String e = FxmlUtils.getInternalizedPropertyByKey("chart.name") + (i+1);
			chartList.add(e);
		}
		return FXCollections.observableList(chartList);
	}
	public void newLineChart() {
		builder = new LineChartBuilder();
		buildersList.add(builder);
		builder.createNew();
		builder.setLegendVisible(true);
		currentLineChart = builder.getResult();
	}
	public void setStyleCssLegendColor(String styleCssLegendColor) {
		currentLineChart.setStyle(styleCssLegendColor);
		updateChart();
	}

	public void deleteChart(String selectedLineChart) {
		final String[] split = selectedLineChart.split(" ");
		int value = Integer.parseInt(split[1])-1;

		buildersList.remove(value);
		updateChart();
	}

	public LineChart<Number, Number> getSelectedLineChart(String selectedLineChart) {
		final String[] split = selectedLineChart.split(" ");
		int value = Integer.parseInt(split[1])-1;

		builder = buildersList.get(value);
		updateChart();
		return builder.getResult();
	}

	public void createSeries(Map<LocalDateTime, Double> xyDataMap, UniNames seriesName, Color color){
		builder.createSeries(xyDataMap, seriesName);
		builder.setSeriesColor(color);
		updateChart();
	}
	public void clearSeriesBeforeCreatingNewOne() {
		builder.clear();
		builder.clearSettings();
		updateChart();
	}

	public void setLineChartTitle(String title){
		builder.setTitle(title);
		updateChart();
	}

	public void setXAxisLabel(String label){
		builder.setXAxisLabel(label);
		updateChart();
	}

	public void setYAxisLabel(String label){
		builder.setYAxisLabel(label);
		updateChart();
	}

	public void setXDateTickToOnlyTime() {
		builder.setXDateTickToOnlyTime();
	}

	public void setXDateTickToDays() { builder.setXDateTickToDays();
	}

	public void switchCurrentLegendOn() {
		builder.setLegendVisible(true);
		updateChart();
	}
	public void switchCurrentLegendOff() {
		builder.setLegendVisible(false);
		updateChart();
	}

	public void switchCurrentDataPointsOn() {
		builder.setCreateSymbols(true);
		updateChart();
	}

	public void switchCurrentDataPointsOff() {
		builder.setCreateSymbols(false);
		updateChart();
	}

	private void updateChart() {
		currentLineChart = builder.getResult();
	}

	public List<Object> getChartSettingsPane() {
		if(buildersList.size()<1) {return List.of();}
		List<Object> list = new ArrayList<>();
		list.add(builder.getTitle());
		list.add(builder.getXLabel());
		list.add(builder.getYLabel());
		list.add(builder.isLegendVisible());
		list.add(builder.isCreatedSymbols());
		list.add(builder.getYMin());
		list.add(builder.getYMax());
		list.add(builder.getYTick());
		updateChart();
		return list;
	}


	public List<Object> getChartYDataSettings(){
		if(buildersList.size()<2) {return List.of();}
		List<Object> list = new ArrayList<>();
		list.add(builder.getSeriesName());
		list.add(builder.getSeriesColors());
		updateChart();
		return list;
	}

	public void setYMin(double min) {
		builder.setYMin(min);
		updateChart();
	}

	public void setYMax(double max) {
		builder.setYMax(max);
		updateChart();
	}

	public void setYTick(double tick) {
		builder.setYTick(tick);
		updateChart();
	}

	public void saveCurrentSettings(ArrayList<UniNames> yToSave, ArrayList<Color> yColorsToSave) {
		builder.setSeriesNames(yToSave);
		builder.setSeriesColors(yColorsToSave);
		updateChart();
	}
}
