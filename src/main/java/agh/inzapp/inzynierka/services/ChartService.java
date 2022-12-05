package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import com.sun.javafx.charts.Legend;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChartService {
	private ListProperty<LineChart<String, Number>> lineChartObservableList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private LineChart<String, Number> currentLineChart;
	private XYChart.Series<String, Number> currentSeries;
	private int indexOfLineChart;
	private String xTickDatePattern;

	public ChartService() {
		currentSeries = new XYChart.Series<>();
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
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		LineChart<String, Number> newLineChart = new LineChart<>(xAxis, yAxis);
		newLineChart.setCreateSymbols(false);
		newLineChart.setLegendVisible(false);
		newLineChart.setAnimated(false);
		newLineChart.setPrefWidth(Control.USE_COMPUTED_SIZE);
		newLineChart.setPrefHeight(Control.USE_COMPUTED_SIZE);
		newLineChart.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		AnchorPane.setTopAnchor(newLineChart, 0.0);
		AnchorPane.setBottomAnchor(newLineChart, 0.0);
		AnchorPane.setLeftAnchor(newLineChart, 0.0);
		AnchorPane.setRightAnchor(newLineChart, 0.0);
		if(currentLineChart==null){
			indexOfLineChart++;
			lineChartObservableList.add(indexOfLineChart, newLineChart); //dodaj nowy pierwszy
		}else{
			lineChartObservableList.add(indexOfLineChart, currentLineChart); //dodaj do listy ten, co aktualnie edytowany był
			indexOfLineChart++;	//zwieksz index na next nowy
			currentLineChart = newLineChart; //nowy wykres jest obecnym
			lineChartObservableList.set(indexOfLineChart, currentLineChart); // ustaw w liście nowy obecny na nowy index'ie
		}
	}
	public LineChart<String, Number> getSelectedLineChart(String selectedLineChart) {
		final String[] split = selectedLineChart.split(" ");
		int value = Integer.parseInt(split[1])-1;

		final LineChart<String, Number> lineChart = lineChartObservableList.get(value);
		currentLineChart = lineChart;
		return lineChart;
	}

	public void createSeries(Map<LocalDateTime, Double> xyDataMap, UniNames seriesName, Color color){
		currentSeries = new XYChart.Series<>();
		setSeries(xyDataMap);
		setSeriesColor(color);
		setSeriesName(seriesName);
	}

	public void clearSeriesBeforeCreatingNewOne() {
		currentLineChart.getData().clear();
	}

	public void setLineChartTitle(String title){
		currentLineChart.setTitle(title);
		updateChart();
	}

	public void setXAxisLabel(String label){
		currentLineChart.getXAxis().setLabel(label);
		currentLineChart.getXAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: 0 -10 0 0;");
		updateChart();
	}
	public void setYAxisLabel(String label){
		currentLineChart.getYAxis().setTickLabelRotation(-90);
		currentLineChart.getYAxis().setLabel(label);
		currentLineChart.getYAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: -10 0 0 0;");
		updateChart();
	}

	public void setXDateTickToOnlyTime() {
		xTickDatePattern = "HH:mm";
	}

	public void setXDateTickToDays() {
		xTickDatePattern = "d-MMM-yyyy HH:mm";
	}

	public void setDefaultStyleCss(String styleCss) {
		currentLineChart.setStyle(styleCss);
		updateChart();
	}

	public void switchCurrentLegendOn() {
		currentLineChart.setLegendVisible(true);
		updateChart();
	}

	public void switchCurrentLegendOff() {
		currentLineChart.setLegendVisible(false);
		updateChart();
	}

	public void switchCurrentDataPointsOn() {
		currentLineChart.setCreateSymbols(true);
		updateChart();
	}

	public void switchCurrentDataPointsOff() {
		currentLineChart.setCreateSymbols(false);
		updateChart();
	}

	public void setAxisBounds(double min, double max) {
		NumberAxis axis = (NumberAxis) currentLineChart.getYAxis();
		axis.setAutoRanging(false);
		axis.setLowerBound(min);
		axis.setUpperBound(max);
		updateChart();
	}
	private void setSeriesName(UniNames uniNames){
		String seriesName = uniNames.toString()+uniNames.getUnit();
		currentSeries.setName(seriesName);
		updateChart();
	}

	private void setSeries(Map<LocalDateTime, Double> xyDataMap){
		ObservableList<XYChart.Data<String, Number>> dataList = currentSeries.getData();
		dataList.clear();

		xyDataMap.forEach((x,y)->{
			XYChart.Data<String, Number> data = new XYChart.Data<>(x.format(DateTimeFormatter.ofPattern(xTickDatePattern)),y);
			dataList.add(data);
		});
		currentSeries.setData(dataList);
		currentLineChart.getData().add(currentSeries);
		updateChart();
	}

	private void updateChart() {
		lineChartObservableList.set(indexOfLineChart, currentLineChart);
	}

	private void setSeriesColor(Color seriesColor){
		Node line = currentSeries.getNode().lookup(".chart-series-line");
		currentSeries.getNode().getStyleClass().add("chart-line-symbol");
		Node symbol = currentSeries.getNode().lookup(".chart-line-symbol");
//		List<String> styles = currentSeries.getNode().getStyleClass();
//		styles.forEach(System.out::println);

		String rgb = String.format("%d, %d, %d",
				(int) (seriesColor.getRed() * 255),
				(int) (seriesColor.getGreen() * 255),
				(int) (seriesColor.getBlue() * 255));
		symbol.setStyle("-fx-background-color: rgba(" + rgb + ", 1.0);");
		line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");

//		for (int index = 0; index < currentSeries.getData().size(); index++) {
//			XYChart.Data dataPoint = series.getData().get(index);
//			Node lineSymbol = dataPoint.getNode().lookup(".chart-line-symbol");
//			lineSymbol.setStyle("-fx-background-color: #00ff00, #000000; -fx-background-insets: 0, 2;\n" +
//					"    -fx-background-radius: 3px;\n" +
//					"    -fx-padding: 3px;");
//		}
//		Node symbols = currentSeries.getNode().lookup(".chart-line-symbol");
//		symbols.setStyle("-background-color: rgba(" + rgb + ", 1.0);");
	}

	private void setLegendItemColor(String name, String color, Legend lg) {
		for (Node n : lg.getChildren()) {
			Label lb = (Label) n;
			if (lb.getText().contains(name)) {
				lb.getGraphic().setStyle("-fx-background-color:" + color + ";");
			}
		}
	}
}
