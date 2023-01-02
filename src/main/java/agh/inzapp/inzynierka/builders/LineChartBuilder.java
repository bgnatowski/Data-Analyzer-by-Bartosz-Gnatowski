package agh.inzapp.inzynierka.builders;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.CommonUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LineChartBuilder {
	private LineChart<Number, Number> chart;
	private XYChart.Series<Number, Number> series;
	private NumberAxis xAxis;
	private String xTickDatePattern;
	private double yMinTick, yMaxTick, yTick;

	public LineChartBuilder() {
		setXDateTickToDays();
	}

	public void createNew(){
		xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRanging(true);
		xAxis.setAutoRanging(true);
		chart = new LineChart<>(xAxis, yAxis);
		yMinTick = 0;
		yMaxTick = 110;
		yTick = 5;
		setCss("style/default_chart.css");
		setCreateSymbols(false);

		chart.setAnimated(false);
		chart.setPrefWidth(Control.USE_COMPUTED_SIZE);
		chart.setPrefHeight(Control.USE_COMPUTED_SIZE);
		chart.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		AnchorPane.setTopAnchor(chart, 0.0);
		AnchorPane.setBottomAnchor(chart, 0.0);
		AnchorPane.setLeftAnchor(chart, 0.0);
		AnchorPane.setRightAnchor(chart, 0.0);
	}

	public void setLegendVisible(boolean param) {
		chart.setLegendVisible(param);
	}

	public void setCreateSymbols(boolean param){
		chart.setCreateSymbols(param);
	}

	public void setTitle(String title){
		chart.setTitle(title);
	}

	public void setYAxisLabel(String label){
		chart.getYAxis().setLabel(label);
		chart.getYAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: -10 0 0 0;");
		chart.getYAxis().setTickLabelRotation(-90);
	}

	public void setXAxisLabel(String label){
		chart.getXAxis().setLabel(label);
		chart.getXAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: 0 -10 0 0;");
	}

	public void createSeries(Map<LocalDateTime, Double> xyDataMap, UniNames name){
		series = new XYChart.Series<>();
		setSeriesName(name);

		ObservableList<XYChart.Data<Number, Number>> dataList = FXCollections.observableArrayList();
		xyDataMap.forEach((x,y)->{
			final long longDateOnX = x.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			XYChart.Data<Number, Number> data = new XYChart.Data<>(longDateOnX,y);
			dataList.add(data);
		});
		setTimeAxisTick(xyDataMap);
		setYAxisTick(xyDataMap);
		series.setData(dataList);
		chart.getData().add(series);
	}

	private void setYAxisTick(Map<LocalDateTime, Double> xyDataMap) {
		Double min = Collections.min(xyDataMap.values());
		Double max = Collections.max(xyDataMap.values());
		double off = max-min;

		yMinTick = min-off;
		yMaxTick = max+off;
		yTick = (max-min)/2;
		setYAxisBounds(yMinTick, yMaxTick, yTick);
		setTickLabelFormatterOnX();
	}

	private void setTimeAxisTick(Map<LocalDateTime, Double> xyDataMap) {
		final List<LocalDateTime> localDateTimes = xyDataMap.keySet().stream().toList();
		long min = localDateTimes.get(0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		long max = localDateTimes.get(localDateTimes.size()-1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		long off = max-min;
		setXAxisBounds(min, max, off/(localDateTimes.size()/2d));
		setTickLabelFormatterOnX();
	}

	public void setSeriesName(UniNames name) {
		String seriesName = name.toString()+ name.getUnit();
		series.setName(seriesName);
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

		yMinTick = min;
		yMaxTick = max;
		yTick = tick;
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

	public void setCss(String styleCss) {
		chart.getStylesheets().add(styleCss);
		chart.applyCss();
	}

	public void setSeriesColor(Color seriesColor) {
		Node line = series.getNode().lookup(".chart-series-line");
		String rgb = CommonUtils.convertToWebString(seriesColor);
		line.setStyle("-fx-stroke: "+rgb+";");
	}

	public void clear() {
		chart.getData().clear();
	}

	public String getTitle(){
		return chart.getTitle();
	}

	public String getXLabel(){
		return chart.getXAxis().getLabel();
	}

	public String getYLabel(){
		return chart.getYAxis().getLabel();
	}

	public double getTick(){
		return yTick;
	}

	public double getMinY(){
		return yMinTick;
	}

	public double getMaxY(){
		return yMaxTick;
	}

	public boolean isLegendVisible(){
		return chart.isLegendVisible();
	}

	public boolean isCreatedSymbols(){
		return chart.getCreateSymbols();
	}


}
