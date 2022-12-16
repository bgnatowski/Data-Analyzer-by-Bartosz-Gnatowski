package agh.inzapp.inzynierka.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BarChartService {
	private StackedBarChart<String, Number> barChart;
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private XYChart.Series<String , Number> series1;
	private XYChart.Series<String , Number> series2;
	private XYChart.Series<String , Number> series3;

	public BarChartService() {}

	public void createNew(){
		series1 = new XYChart.Series<>();
		series2 = new XYChart.Series<>();
		series3 = new XYChart.Series<>();
		final List<Integer> from1to50 = IntStream.rangeClosed(1, 50).boxed().collect(Collectors.toList());
		final List<String> x = from1to50.stream().map(i -> String.valueOf(i)).collect(Collectors.toList());
		xAxis = new CategoryAxis(FXCollections.observableArrayList(x));
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
		ObservableList<XYChart.Data<String , Number>> dataList = setSeriesData(avgHarmoList);
		series1.setData(dataList);
	}

	public void set95Series(List<Double> harmo95List){
		series2.setName("95%");
		ObservableList<XYChart.Data<String, Number>> dataList = setSeriesData(harmo95List);
		series2.setData(dataList);
	}

	public void setMaxSeries(List<Double> maxHarmoList){
		series3.setName("max");
		ObservableList<XYChart.Data<String, Number>> dataList = setSeriesData(maxHarmoList);
		series3.setData(dataList);
	}

	public void setYAxisBounds(double min, double max, double tick) {
		NumberAxis axis = (NumberAxis) barChart.getYAxis();
		axis.setAutoRanging(false);
		axis.setLowerBound(min);
		axis.setUpperBound(max);
		axis.setTickUnit(tick);
	}


	public StackedBarChart<String, Number> getResult() {
		barChart.getData().addAll(series1, series2, series3);
		barChart.setCategoryGap(1);
		return barChart;
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
