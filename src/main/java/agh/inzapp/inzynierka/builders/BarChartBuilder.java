package agh.inzapp.inzynierka.builders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BarChartBuilder {
	private BarChart<String, Number> barChart;
	private XYChart.Series<String , Number> series1;
	private XYChart.Series<String , Number> series2;
	private XYChart.Series<String , Number> series3;

	public void createNew(int howMany){
		series1 = new XYChart.Series<>();
		series2 = new XYChart.Series<>();
		series3 = new XYChart.Series<>();

		final List<Integer> from2to50 = IntStream.rangeClosed(2, howMany).boxed().toList();
		final List<String> x = from2to50.stream().map(String::valueOf).collect(Collectors.toList());

		CategoryAxis xAxis = new CategoryAxis(FXCollections.observableArrayList(x));
		NumberAxis yAxis = new NumberAxis();
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

		setXAxisLabel();
		setYAxisLabel();
	}
	public void setTitle(String title){
		barChart.setTitle(title);
	}
	public void setYData(List<Double> avgHarmoList, String seriesName){
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
		barChart.setCategoryGap(0.1);
		barChart.setBarGap(0);
	}

	public BarChart<String, Number> getResult() {
		final URL resource = getClass().getResource(("/style/default_chart.css"));
		barChart.getStylesheets().add(Objects.requireNonNull(resource).toExternalForm());
		barChart.applyCss();

		return barChart;
	}

	private void setXAxisLabel(){
		barChart.getXAxis().setLabel("Harmoniczne [-]");
		barChart.getXAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: 0 -10 0 0;");
		barChart.getXAxis().setTickLabelRotation(-90);
		barChart.getXAxis().setTickLength(1);
		barChart.getXAxis().setTickLabelsVisible(true);
		barChart.getXAxis().setTickLabelGap(2);
		barChart.getXAxis().setAnimated(false);
	}

	private void setYAxisLabel(){
		barChart.getYAxis().setLabel("Amplituda [%]");
		barChart.getYAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: -15 0 0 0;");
//		barChart.getYAxis().setTickLabelRotation(-90);
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
}
