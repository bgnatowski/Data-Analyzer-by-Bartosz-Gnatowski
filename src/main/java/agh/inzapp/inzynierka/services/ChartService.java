package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ChartService {
	private ListProperty<LineChart<String, Number>> lineChartObservableList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private LineChart<String, Number> currentLineChart;
	private XYChart.Series currentSeries;
	private int indexOfLineChart;

	public ChartService() {
		currentSeries = new XYChart.Series();
		indexOfLineChart = -1;
//		testChart();
	}
	private void testChart() {
		CategoryAxis xAxis = new CategoryAxis(FXCollections.observableList(Arrays.asList("0", "10", "2")));
		NumberAxis yAxis = new NumberAxis(1.3378, 1.3390, 0.0001);
		xAxis.setLabel("xAxis");
		yAxis.setLabel("yAxis");
		yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis){
			@Override public String toString(Number object){
				return String.format("%1.4f", object); }
		});
		LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
		XYChart.Series series = new XYChart.Series();

		final XYChart.Data d1 = new XYChart.Data("0.0",1.3379);
		final XYChart.Data d2 = new XYChart.Data("2.0",1.3387);
		final XYChart.Data d3 = new XYChart.Data("2.5",1.3385);
		final XYChart.Data d4 = new XYChart.Data("3.5",1.3387);
		final XYChart.Data d5 = new XYChart.Data("8.0",1.3378);
		final XYChart.Data d6 = new XYChart.Data("9.5",1.3388);
		series.getData().addAll(d1, d2, d3, d4, d5, d6);
		lineChart.getData().add(series);
		lineChart.setLegendVisible(true);
		lineChart.setPrefWidth(Control.USE_COMPUTED_SIZE);
		lineChart.setPrefHeight(Control.USE_COMPUTED_SIZE);
		lineChart.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		AnchorPane.setTopAnchor(lineChart, 0.0);
		AnchorPane.setBottomAnchor(lineChart, 0.0);
		AnchorPane.setLeftAnchor(lineChart, 0.0);
		AnchorPane.setRightAnchor(lineChart, 0.0);
		lineChartObservableList.add(lineChart);
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
		xAxis.setLabel("xAxis");
		yAxis.setLabel("yAxis");
		yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis){
			@Override public String toString(Number object){
				return String.format("%1.4f", object); }
		});
		LineChart<String, Number> newLineChart = new LineChart<>(xAxis, yAxis);
		newLineChart.setLegendVisible(true);
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
			lineChartObservableList.add(indexOfLineChart, currentLineChart); //dodaj do listy ten co aktualnie edytowany był
			indexOfLineChart++;	//zwieksz index na next nowy
			currentLineChart = newLineChart; //nowy wykres jest obecnym
			lineChartObservableList.set(indexOfLineChart, currentLineChart); // ustaw w liscie nowy obecny na nowy indexie
		}
	}
	public LineChart<String, Number> getSelectedLineChart(String selectedLineChart) {
		final String[] split = selectedLineChart.split(" ");
		int value = Integer.valueOf(split[1])-1;

		final LineChart<String, Number> lineChart = lineChartObservableList.get(value);
		currentLineChart = lineChart;
		return lineChart;
	}

	public void addSeriesToChart(Map<LocalDateTime, Double> xyDataMap){
		currentSeries = new XYChart.Series();

//		currentSeries.getNode().setStyle("-fx-stroke:"+xColorValueColor+";");
		setSeries(xyDataMap);
	}

	public void setSeriesColor(Color seriesColor){
		Node line = currentSeries.getNode().lookup(".chart-series-line");
		String rgb = String.format("%d, %d, %d",
				(int) (seriesColor.getRed() * 255),
				(int) (seriesColor.getGreen() * 255),
				(int) (seriesColor.getBlue() * 255));
		line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
	}

	public void setSeriesName(UniNames seriesName){
		currentSeries.setName(seriesName.toString()+seriesName.getUnit());
	}

	private void setSeries(Map<LocalDateTime, Double> xyDataMap){
		ObservableList dataList = currentSeries.getData();
		dataList.clear();
		xyDataMap.forEach((x,y)->{
			XYChart.Data data = new XYChart.Data(x,y);
			dataList.add(data);
		});
		currentSeries.setData(dataList);
	}

}
