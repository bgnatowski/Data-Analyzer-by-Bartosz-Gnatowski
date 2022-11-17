package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.enums.LineStyles;
import agh.inzapp.inzynierka.models.ListDataFx;
import agh.inzapp.inzynierka.models.ListHarmoFx;
import agh.inzapp.inzynierka.models.enums.UniNames;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Controller;

import java.awt.event.ActionEvent;
import java.time.LocalTime;

@Controller
public class ChartViewController {
	@FXML
	private Button addToRaportButton;
	@FXML
	private AnchorPane apOfChart;
	@FXML
	private Button saveAsChartButton;
	@FXML
	private Button xAddButton;
	@FXML
	private ColorPicker xColor;
	@FXML
	private DatePicker xDateFrom;
	@FXML
	private DatePicker xDateTo;
	@FXML
	private Spinner<LocalTime> xTimeFrom;
	@FXML
	private Spinner<LocalTime> xTimeTo;
	@FXML
	private Button yAddButton;
	@FXML
	private Button yAddValueButton;
	@FXML
	private ColorPicker yColor;
	@FXML
	private GridPane yGrid;
	@FXML
	private ComboBox<LineStyles> yLineStyle;
	@FXML
	private ComboBox<UniNames> yValue;
	@FXML
	void addChartToRaportOnAction(ActionEvent event) {

	}

	@FXML
	void saveAsChartOnAction(ActionEvent event) {

	}

	@FXML
	void xAddOnAction(ActionEvent event) {

	}

	@FXML
	void yAddOnAction(ActionEvent event) {

	}

	@FXML
	void yAddValueOnAction(ActionEvent event) {

	}

	@FXML
	void addXDataOnAction(ActionEvent event) {

	}
	@FXML
	void addYDataOnAction(ActionEvent event) {

	}
	@FXML
	void addYDataOnAction1(ActionEvent event) {

	}
	@FXML
	void addYValueOnAction(ActionEvent event) {

	}

	private static ListDataFx listDataFx;
	private static ListHarmoFx listHarmoFx;

	@FXML
	public void initialize(){
		listDataFx = ListDataFx.getInstance();
		listHarmoFx = ListHarmoFx.getInstance();
		bindings();
	}

	private void bindings() {
		final NumberAxis xAxis = new NumberAxis(0, 10, 2);
		final NumberAxis yAxis = new NumberAxis(1.3378, 1.3390, 0.0001);
		xAxis.setLabel("xAxis");
		yAxis.setLabel("yAxis");
		yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis){
			@Override public String toString(Number object){
				return String.format("%1.4f", object); }
		});
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		XYChart.Series series = new XYChart.Series();

		final XYChart.Data d1 = new XYChart.Data(0.0,1.3379);
		final XYChart.Data d2 = new XYChart.Data(2.0,1.3387);
		final XYChart.Data d3 = new XYChart.Data(2.5,1.3385);
		final XYChart.Data d4 = new XYChart.Data(3.5,1.3387);
		final XYChart.Data d5 = new XYChart.Data(8.0,1.3378);
		final XYChart.Data d6 = new XYChart.Data(9.5,1.3388);

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
		apOfChart.getChildren().add(lineChart);
	}

	@FXML
	private void yAddValueOnAction(javafx.event.ActionEvent event) {
	}
	@FXML
	private void yAddOnAction(javafx.event.ActionEvent event) {
	}
	@FXML
	private void addChartToRaportOnAction(javafx.event.ActionEvent event) {
	}
	@FXML
	private void saveAsChartOnAction(javafx.event.ActionEvent event) {
	}
	@FXML
	private void xAddOnAction(javafx.event.ActionEvent event) {
	}
}
