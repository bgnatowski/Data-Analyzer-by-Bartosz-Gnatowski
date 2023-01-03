package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.builders.LineChartBuilder;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserChartService {
	private final ListProperty<LineChart<Number, Number>> lineChartObservableList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final List<LineChartBuilder> buildersList;
	private LineChart<Number, Number> currentLineChart;
	private LineChartBuilder builder;
	private int indexOfLineChart;

	public UserChartService() {
		buildersList = new ArrayList<>();
		indexOfLineChart = -1;
	}
	public ObservableList<String> getLineChartsList() {
		List<String> chartList = new ArrayList<>();
		for(int i = 1; i <= lineChartObservableList.size(); i++){
			final String e = FxmlUtils.getInternalizedPropertyByKey("chart.name") + i;
			chartList.add(e);
		}
		return FXCollections.observableList(chartList);
	}
	public void newLineChart() {
		builder = new LineChartBuilder();
		buildersList.add(builder);
		builder.createNew();
		builder.setLegendVisible(false);
		if(lineChartObservableList.isEmpty()){
			indexOfLineChart++;
			currentLineChart = builder.getResult();
			lineChartObservableList.add(indexOfLineChart, currentLineChart); //dodaj nowy pierwszy
		}else{
			lineChartObservableList.add(indexOfLineChart, currentLineChart); //dodaj do listy ten, co aktualnie edytowany był
			indexOfLineChart++;	//zwiększ index na next nowy
			currentLineChart = builder.getResult(); //nowy wykres jest obecnym
			lineChartObservableList.set(indexOfLineChart, currentLineChart); // ustaw w liście nowy obecny na nowy index'ie
		}
	}
	public void setStyleCssLegendColor(String styleCssLegendColor) {
		currentLineChart.setStyle(styleCssLegendColor);
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

	public void setAxisBounds(double min, double max, double tick) {
		builder.setYAxisBounds(min, max, tick);
		updateChart();
	}

	private void updateChart() {
		currentLineChart = builder.getResult();
		lineChartObservableList.set(indexOfLineChart, currentLineChart);
	}

	public List<Object> getChartSettings() {
		if(lineChartObservableList.isEmpty()) {return List.of();}
		List<Object> list = new ArrayList<>();
		list.add(builder.getTitle());
		list.add(builder.getXLabel());
		list.add(builder.getYLabel());
		list.add(builder.isLegendVisible());
		list.add(builder.isCreatedSymbols());
		list.add(builder.getMinY());
		list.add(builder.getMaxY());
		list.add(builder.getTick());
		return list;
	}
}
