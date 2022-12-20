package agh.inzapp.inzynierka.builders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BarChartBuilder {
	private BarChart<String, Number> barChart;
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private XYChart.Series<String , Number> series1;
	private XYChart.Series<String , Number> series2;
	private XYChart.Series<String , Number> series3;

	public void createNew(){
		series1 = new XYChart.Series<>();
		series2 = new XYChart.Series<>();
		series3 = new XYChart.Series<>();

		final List<Integer> from1to50 = IntStream.rangeClosed(1, 50).boxed().collect(Collectors.toList());
		final List<String> x = from1to50.stream().map(i -> String.valueOf(i)).collect(Collectors.toList());

		xAxis = new CategoryAxis(FXCollections.observableArrayList(x));
		yAxis = new NumberAxis();
		barChart = new BarChart<>(xAxis, yAxis);
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
	public void setSeries(List<Double> avgHarmoList, String seriesName){
		ObservableList<XYChart.Data<String , Number>> dataList = setSeriesData(avgHarmoList);
		switch (seriesName){
			case "max" ->{
				series1.setName(seriesName);
				series1.setData(dataList);
			}
			case "95%" ->{
				series2.setName(seriesName);
				series2.setData(dataList);
			}
			case "avg" ->{
				series3.setName(seriesName);
				series3.setData(dataList);
			}
		}
	}
	public void updateSeries() {
		barChart.getData().addAll(series1, series2, series3);
		barChart.setCategoryGap(0);
		barChart.setBarGap(0);
	}

	public void setYAxisBounds(double min, double max, double tick) {
		NumberAxis axis = (NumberAxis) barChart.getYAxis();
		axis.setAutoRanging(false);
		axis.setLowerBound(min);
		axis.setUpperBound(max);
		axis.setTickUnit(tick);
	}


	public BarChart<String, Number> getResult() {
		barChart.getStylesheets().add("style/default_chart.css");
		barChart.applyCss();

		return barChart;
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

	private void setStyleCss(String seriesName) {
		switch (seriesName){
			case "max" ->{
				barChart.getStylesheets().add("style/barchart1.css");
				barChart.setBarGap(0.33);
			}
			case "95%" ->{
				barChart.getStylesheets().add("style/barchart2.css");
				barChart.setBarGap(0.66);
			}
			case "avg" ->{
				barChart.getData().add(series2);
				barChart.getData().add(series3);
				barChart.getStylesheets().add("style/barchart3.css");
				barChart.setBarGap(0.99);
			}
		}
		barChart.applyCss();
	}
	private ObservableList<XYChart.Data<String, Number>> setSeriesData(List<Double> harmoList) {
		ObservableList<XYChart.Data<String, Number>> dataList = FXCollections.observableArrayList();
		for(int x = 0; x < harmoList.size(); x++){
			final Double y = harmoList.get(x);
			XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(++x), y);
			dataList.add(data);
		}
		return dataList;
	}

	public void setBarWidth(double barWidth, double availableSpace) {
		int dataSeriesCount = barChart.getData().size();
		int categoriesCount = xAxis.getCategories().size();

		if (dataSeriesCount <= 1) {
			barChart.setBarGap(0d);
		} else {
			barChart.setBarGap(5d);
		}

		double barWidthSum = barWidth * (categoriesCount * dataSeriesCount);
		double barGapSum = barChart.getBarGap() * (dataSeriesCount - 1);
		double categorySpacing = (availableSpace - barWidthSum - barGapSum) / categoriesCount;
		barChart.setCategoryGap(categorySpacing);
	}
}
