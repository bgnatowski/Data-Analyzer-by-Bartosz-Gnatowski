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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartService {
	private ListProperty<LineChart<String, Number>> lineChartObservableList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private LineChart<String, Number> currentLineChart;
	private XYChart.Series currentSeries;
	private int indexOfLineChart;
	private String xTickDatePattern;

	public ChartService() {
		currentSeries = new XYChart.Series();
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
		yAxis.setAutoRanging(true);
		yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis){
			@Override public String toString(Number object){
				return String.format("%1.2f", object); }
		});
		LineChart<String, Number> newLineChart = new LineChart<>(xAxis, yAxis);
		newLineChart.setCreateSymbols(false);
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

	public void createSeries(Map<LocalDateTime, Double> xyDataMap, UniNames seriesName, Color color){
		currentSeries = new XYChart.Series();
		setSeries(xyDataMap);
		setSeriesName(seriesName);
		updateCurrentLineChart();
//		setSeriesColor(color);
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
			XYChart.Data data = new XYChart.Data(x.format(DateTimeFormatter.ofPattern(xTickDatePattern)),y);
			dataList.add(data);
		});
		currentSeries.setData(dataList);
	}
	private void updateCurrentLineChart(){
		currentLineChart.getData().add(currentSeries);
		lineChartObservableList.set(indexOfLineChart, currentLineChart);
	}

	public void clearSeriesBeforeCreatingNewOne() {
		currentLineChart.getData().clear();
	}

	public void setLineChartTitle(String title){
		currentLineChart.setTitle(title);
		updateCurrentLineChart();
	}

	public void setXDateTickToOnlyTime() {
		xTickDatePattern = "HH:mm";
	}

	public void setXDateTickToDays() {
		xTickDatePattern = "d-MMM-yyyy HH:mm";
	}
}
