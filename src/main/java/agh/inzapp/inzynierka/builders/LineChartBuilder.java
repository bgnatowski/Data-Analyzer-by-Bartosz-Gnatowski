package agh.inzapp.inzynierka.builders;

import agh.inzapp.inzynierka.models.enums.UniNames;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LineChartBuilder {
	private LineChart<Number, Number> chart;
	private XYChart.Series<Number, Number> series;
	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private String xTickDatePattern;

	public LineChartBuilder() {
		setXDateTickToDays();
	}

	public void createNew(){
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		yAxis.setAutoRanging(true);
		xAxis.setAutoRanging(true);
		chart = new LineChart<>(xAxis,yAxis);
		chart.getStylesheets().add("style/default_chart.css");
		chart.applyCss();
		chart.setCreateSymbols(false);
		chart.setLegendVisible(true);
		chart.setAnimated(false);
		chart.setPrefWidth(Control.USE_COMPUTED_SIZE);
		chart.setPrefHeight(Control.USE_COMPUTED_SIZE);
		chart.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		AnchorPane.setTopAnchor(chart, 0.0);
		AnchorPane.setBottomAnchor(chart, 0.0);
		AnchorPane.setLeftAnchor(chart, 0.0);
		AnchorPane.setRightAnchor(chart, 0.0);
	}

	public void setTitle(String title){
		chart.setTitle(title);
	}

	public void setYAxisLabel(String label){
		chart.getYAxis().setTickLabelRotation(-90);
		chart.getYAxis().setLabel(label);
		chart.getYAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: -10 0 0 0;");
	}

	public void setXAxisLabel(String label){
		chart.getXAxis().setLabel(label);
		chart.getXAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: 0 -10 0 0;");
	}

	public void createSeries(Map<LocalDateTime, Double> xyDataMap, UniNames name){
		series = new XYChart.Series<>();
		String seriesName = name.toString()+name.getUnit();
		series.setName(seriesName);

		ObservableList<XYChart.Data<Number, Number>> dataList = FXCollections.observableArrayList();
		xyDataMap.forEach((x,y)->{
			final long longDateOnX = x.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			XYChart.Data<Number, Number> data = new XYChart.Data<>(longDateOnX,y);
			dataList.add(data);
		});

		final List<LocalDateTime> localDateTimes = xyDataMap.keySet().stream().toList();
		long min = localDateTimes.get(0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		long max = localDateTimes.get(localDateTimes.size()-1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		long off = max-min;
		System.out.println(min + " " + max + " " + off);
		setXAxisBounds(min, max, off/(localDateTimes.size()/2));
		setTickLabelFormatterOnX();

		series.setData(dataList);
		chart.getData().add(series);
	}

	private void setTickLabelFormatterOnX() {
		xAxis.setTickLabelFormatter(new StringConverter<>() {
			@Override
			public String toString(Number number) {
				return Instant.ofEpochMilli(number.longValue()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern(xTickDatePattern));
			}
			@Override
			public Number fromString(String s) {
				return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(xTickDatePattern)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			}
		});
	}

	public void setXDateTickToOnlyTime() { xTickDatePattern = "HH:mm";}
	public void setXDateTickToDays() {
		xTickDatePattern = "d-MM HH:mm";
	}

	public void setYAxisBounds(double min, double max, double tick) {
		NumberAxis axis = (NumberAxis) chart.getYAxis();
		axis.setAutoRanging(false);
		axis.setLowerBound(min);
		axis.setUpperBound(max);
		axis.setTickUnit(tick);
	}

	public void setXAxisBounds(double min, double max, double tick) {
		NumberAxis axis = (NumberAxis) chart.getXAxis();
		axis.setTickLabelRotation(-90);
		axis.setAutoRanging(false);
		axis.setLowerBound(min);
		axis.setUpperBound(max);
		axis.setTickUnit(tick);
	}

	public LineChart<Number, Number> getResult() {
		return chart;
	}

	public void setTimeTick() {
		setXDateTickToOnlyTime();
	}
}
