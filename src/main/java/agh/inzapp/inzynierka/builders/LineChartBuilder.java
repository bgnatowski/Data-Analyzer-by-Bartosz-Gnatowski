package agh.inzapp.inzynierka.builders;

import agh.inzapp.inzynierka.models.enums.UniNames;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class LineChartBuilder {
	private LineChart<String, Number> currentLineChart;
	private XYChart.Series<String, Number> currentSeries;
	private XYChart.Series<String , Number> series;
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private String xTickDatePattern;

	public LineChartBuilder() {
		setXDateTickToDays();
	}

	public void createNew(){
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		yAxis.setAutoRanging(true);
		currentLineChart = new LineChart<>(xAxis,yAxis);
		currentLineChart.getStylesheets().add("style/default_chart.css");
		currentLineChart.applyCss();
		currentLineChart.setCreateSymbols(false);
		currentLineChart.setLegendVisible(true);
		currentLineChart.setAnimated(false);
		currentLineChart.setPrefWidth(Control.USE_COMPUTED_SIZE);
		currentLineChart.setPrefHeight(Control.USE_COMPUTED_SIZE);
		currentLineChart.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		AnchorPane.setTopAnchor(currentLineChart, 0.0);
		AnchorPane.setBottomAnchor(currentLineChart, 0.0);
		AnchorPane.setLeftAnchor(currentLineChart, 0.0);
		AnchorPane.setRightAnchor(currentLineChart, 0.0);
	}

	public void setTitle(String title){
		currentLineChart.setTitle(title);
	}

	public void setYAxisLabel(String label){
		currentLineChart.getYAxis().setTickLabelRotation(-90);
		currentLineChart.getYAxis().setLabel(label);
		currentLineChart.getYAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: -10 0 0 0;");
	}

	public void setXAxisLabel(String label){
		currentLineChart.getXAxis().setLabel(label);
		currentLineChart.getXAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: 0 -10 0 0;");
	}

	public void createSeries(Map<LocalDateTime, Double> xyDataMap, UniNames name){
		currentSeries = new XYChart.Series<>();
		String seriesName = name.toString()+name.getUnit();
		currentSeries.setName(seriesName);

		ObservableList<XYChart.Data<String, Number>> dataList = FXCollections.observableArrayList();
		xyDataMap.forEach((x,y)->{
			XYChart.Data<String, Number> data = new XYChart.Data<>(x.format(DateTimeFormatter.ofPattern(xTickDatePattern)),y);
			dataList.add(data);
		});
		currentSeries.setData(dataList);
		currentLineChart.getData().add(currentSeries);
	}

	public void setXDateTickToOnlyTime() { xTickDatePattern = "HH:mm";}
	public void setXDateTickToDays() {
		xTickDatePattern = "HH:mm d-MM";
	}

	public void setAxisBounds(double min, double max, double tick) {
		NumberAxis axis = (NumberAxis) currentLineChart.getYAxis();
		axis.setAutoRanging(false);
		axis.setLowerBound(min);
		axis.setUpperBound(max);
		axis.setTickUnit(tick);
	}


	public void setAxisBounds(double min, double max) {
		NumberAxis axis = (NumberAxis) currentLineChart.getYAxis();
		axis.setAutoRanging(false);
		axis.setLowerBound(min);
		axis.setUpperBound(max);
	}



	public LineChart<String, Number> getResult() {
		return currentLineChart;
	}

	public void setTimeTick() {
		setXDateTickToOnlyTime();
	}
}
