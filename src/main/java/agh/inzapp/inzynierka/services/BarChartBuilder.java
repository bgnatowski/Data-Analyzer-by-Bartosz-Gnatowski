package agh.inzapp.inzynierka.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class BarChartBuilder{
	private StackedBarChart<Number, Number> barChart;
	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private XYChart.Series<Number, Number> series1;
	private XYChart.Series<Number, Number> series2;
	private XYChart.Series<Number, Number> series3;

	public BarChartBuilder() {}

	public void createNew(){
		series1 = new XYChart.Series<>();
		series2 = new XYChart.Series<>();
		series3 = new XYChart.Series<>();
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		barChart = new StackedBarChart<>(xAxis, yAxis);

		barChart.setLegendVisible(true);
		barChart.setAnimated(false);
		barChart.setPrefWidth(Control.USE_COMPUTED_SIZE);
		barChart.setPrefHeight(Control.USE_COMPUTED_SIZE);
		barChart.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		AnchorPane.setTopAnchor(barChart, 0.0);
		AnchorPane.setBottomAnchor(barChart, 0.0);
		AnchorPane.setLeftAnchor(barChart, 0.0);
		AnchorPane.setRightAnchor(barChart, 0.0);

		setXAxisLabel("Harmoniczne [-]");
		setYAxisLabel("Amplituda [%]");
	}

	public void setTitle(String title){
		barChart.setTitle(title);
	}

	public void setAvgSeries(List<Double> avgHarmoList){
		series1.setName("avg");
		ObservableList<XYChart.Data<Number, Number>> dataList = setSeriesData(avgHarmoList);
		series1.setData(dataList);
	}

	public void set95Series(List<Double> harmo95List){
		series2.setName("95%");
		ObservableList<XYChart.Data<Number, Number>> dataList = setSeriesData(harmo95List);
		series2.setData(dataList);
	}

	public void setMaxSeries(List<Double> maxHarmoList){
		series2.setName("max");
		ObservableList<XYChart.Data<Number, Number>> dataList = setSeriesData(maxHarmoList);
		series2.setData(dataList);
	}

	public void setYAxisBounds(double min, double max, double tick) {
		NumberAxis axis = (NumberAxis) barChart.getYAxis();
		axis.setAutoRanging(false);
		axis.setLowerBound(min);
		axis.setUpperBound(max);
		axis.setTickUnit(tick);
	}

	public void setXAxisBounds(double min, double max, double tick) {
		NumberAxis axis = (NumberAxis) barChart.getXAxis();
		axis.setAutoRanging(false);
		axis.setLowerBound(min);
		axis.setUpperBound(max);
		axis.setTickUnit(tick);
	}


	public StackedBarChart<Number, Number> getResult() {
		barChart.getData().addAll(series1, series2, series3);
		setXAxisBounds(0,50,1);
		return barChart;
	}

	private ObservableList<XYChart.Data<Number, Number>> setSeriesData(List<Double> harmoList) {
		ObservableList<XYChart.Data<Number, Number>> dataList = FXCollections.observableArrayList();
		for(int x = 0; x < harmoList.size(); x++){
			final Double y = harmoList.get(x);
			XYChart.Data<Number, Number> data = new XYChart.Data<>(++x, y);
			dataList.add(data);
		}
		return dataList;
	}

	private void setXAxisLabel(String label){
		barChart.getXAxis().setLabel(label);
		barChart.getXAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: 0 -10 0 0;");
	}

	private void setYAxisLabel(String label){
		barChart.getYAxis().setTickLabelRotation(-90);
		barChart.getYAxis().setLabel(label);
		barChart.getYAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: -10 0 0 0;");
	}
}
