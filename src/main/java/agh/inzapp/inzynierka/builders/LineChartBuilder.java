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
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class LineChartBuilder {
	private LineChart<Number, Number> chart;
	private XYChart.Series<Number, Number> series;
	private NumberAxis xAxis, yAxis;
	private String xTickDatePattern;
	private double yMin, yMax, yTick;
	private LocalDateTime xMin, xMax;
	private List<UniNames> seriesNames;
	private List<Color> seriesColors;

	public LineChartBuilder() {
		setXDateTickToDays();
	}

	public void createNew() {
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		yAxis.setAutoRanging(true);
		xAxis.setAutoRanging(true);
		setTickLabelFormatterOnY();
		chart = new LineChart<>(xAxis, yAxis);
		yMin = 0;
		yMax = 110;
		yTick = 5;
		setCss();
		setCreateSymbols(false);

		chart.setAnimated(false);
		chart.setPrefWidth(Control.USE_COMPUTED_SIZE);
		chart.setPrefHeight(Control.USE_COMPUTED_SIZE);
		chart.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		AnchorPane.setTopAnchor(chart, 0.0);
		AnchorPane.setBottomAnchor(chart, 0.0);
		AnchorPane.setLeftAnchor(chart, 0.0);
		AnchorPane.setRightAnchor(chart, 0.0);

		seriesNames = new ArrayList<>();
		seriesColors = new ArrayList<>();
	}

	public void createSeries(Map<LocalDateTime, Double> xyDataMap, UniNames name) {
		series = new XYChart.Series<>();
		setSeriesName(name);

		ObservableList<XYChart.Data<Number, Number>> dataList = FXCollections.observableArrayList();
		xyDataMap.forEach((x, y) -> {
			final long longDateOnX = x.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			XYChart.Data<Number, Number> data = new XYChart.Data<>(longDateOnX, y);
			dataList.add(data);
		});
		setTimeAxisTick(xyDataMap);
		if(yMin==0d) setYAxisTick(xyDataMap);
		series.setData(dataList);
		chart.getData().add(series);
	}

	private void setYAxisTick(Map<LocalDateTime, Double> xyDataMap) {
		yMin = 0d;
		yMax = Collections.max(xyDataMap.values());
		yTick = (yMax - yMin) / 2;
		setYAxisBounds() ;
	}

	private void setTimeAxisTick(Map<LocalDateTime, Double> xyDataMap) {
		final List<LocalDateTime> localDateTimes = xyDataMap.keySet().stream().toList();
		xMin = localDateTimes.get(0);
		xMax =localDateTimes.get(localDateTimes.size() - 1);
		long min = xMin.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		long max = xMax.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		long off = max - min;
		setXAxisBounds(min, max, off / (localDateTimes.size() / 2d));
		setTickLabelFormatterOnX();
	}

	public void setSeriesName(UniNames name) {
		String seriesName = name + name.getUnit();
		if (seriesName.contains(" śr.")) {
			seriesName = seriesName.replaceAll(" śr.", "");
		}
		if (seriesName.contains(" avg.")) {
			seriesName = seriesName.replaceAll(" avg.", "");
		}
		if (seriesName.contains(" Σ")) {
			seriesName = seriesName.replaceAll(" Σ", "");
		}
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

	private void setTickLabelFormatterOnY() {
		yAxis.setTickLabelFormatter(new StringConverter<>() {
			@Override
			public Number fromString(String string) {
				return Double.parseDouble(string);
			}

			@Override
			public String toString(Number value) {
				return String.format("%2.2f", value.doubleValue());
			}

		});
	}

	public void setXDateTickToOnlyTime() {
		xTickDatePattern = "HH:mm";
	}

	public void setXDateTickToDays() {
		xTickDatePattern = "d-MM HH:mm";
	}
	private void setYAxisBounds(double min, double max, double tick) {
		NumberAxis axis = (NumberAxis) chart.getYAxis();
		axis.setAutoRanging(false);
		axis.setLowerBound(min);
		axis.setUpperBound(max);
		axis.setTickUnit(tick);
	}

	private void setYAxisBounds() {
		NumberAxis axis = (NumberAxis) chart.getYAxis();
		axis.setAutoRanging(false);
		axis.setLowerBound(yMin);
		axis.setUpperBound(yMax);
		axis.setTickUnit(yTick);
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
		setYAxisBounds();
		return chart;
	}

	public void setCss() {
		final URL resource = getClass().getResource(("/style/default_chart.css"));
		chart.getStylesheets().add(Objects.requireNonNull(resource).toExternalForm());
		chart.applyCss();
	}

	public void setSeriesColor(Color seriesColor) {
		Node line = series.getNode().lookup(".chart-series-line");
		String rgb = CommonUtils.convertToWebString(seriesColor);
		line.setStyle("-fx-stroke: " + rgb + ";");
	}

	public void clear() {
		chart.getData().clear();
	}


	//SETTING
	public void setLegendVisible(boolean param) {
		chart.setLegendVisible(param);
	}

	public void setCreateSymbols(boolean param) {
		chart.setCreateSymbols(param);
	}

	public String getTitle() {
		return chart.getTitle();
	}

	public void setTitle(String title) {
		chart.setTitle(title);
	}

	public void setYAxisLabel(String label) {
		chart.getYAxis().setTickLabelRotation(-90);
		chart.getYAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: -15 0 0 0;");
		chart.getYAxis().applyCss();
		chart.getYAxis().setLabel(label);
		setYAxisBounds();
	}

	public void setXAxisLabel(String label) {
		chart.getXAxis()
				.lookup(".axis-label")
				.setStyle("-fx-label-padding: 0 -10 0 0;");
		chart.getXAxis().applyCss();
		chart.getXAxis().setLabel(label);

	}

	public String getXLabel() {
		return chart.getXAxis().getLabel();
	}

	public String getYLabel() {
		return chart.getYAxis().getLabel();
	}

	public boolean isLegendVisible() {
		return chart.isLegendVisible();
	}

	public boolean isCreatedSymbols() {
		return chart.getCreateSymbols();
	}

	public void setYMin(double yMin) {
		this.yMin = yMin;
		setYAxisBounds();
	}

	public void setYMax(double yMax) {
		this.yMax = yMax;
		setYAxisBounds();
	}

	public void setYTick(double yTick) {
		this.yTick = yTick;
		setYAxisBounds();
	}

	public double getYMin() {
		return yMin;
	}

	public double getYMax() {
		return yMax;
	}

	public double getYTick() {
		return yTick;
	}

	public LocalDateTime getXMin() {
		return xMin;
	}

	public LocalDateTime getXMax() {
		return xMax;
	}

	public List<UniNames> getSeriesName() {
		return seriesNames;
	}

	public List<Color> getSeriesColors() {
		return seriesColors;
	}

	public void clearSettings() {
		seriesColors.clear();
		seriesNames.clear();
	}

	public void setSeriesNames(List<UniNames> seriesNames) {
		this.seriesNames = seriesNames;
	}

	public void setSeriesColors(List<Color> seriesColors) {
		this.seriesColors = seriesColors;
	}
}
