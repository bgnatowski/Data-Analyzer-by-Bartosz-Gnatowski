package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.models.enums.AnalyzersModels;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.*;
import agh.inzapp.inzynierka.services.BarChartBuilder;
import agh.inzapp.inzynierka.services.LineChartService;
import agh.inzapp.inzynierka.services.ReportService;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static agh.inzapp.inzynierka.models.enums.UniNames.*;
import static agh.inzapp.inzynierka.utils.FxmlUtils.restrictDatePicker;

@Controller
public class ReportViewController {
	private static final BooleanProperty toggleProperty = new SimpleBooleanProperty(false);
	@FXML
	private GridPane timeGrid;
	@FXML
	private DatePicker dateFrom, dateTo;
	@FXML
	private ComboBox<AnalyzersModels> modelComboBox;
	@FXML
	private Button generateButton;
	@FXML
	private TextField switchboard, measurementPoint, serialNumber;
	@FXML
	private AnchorPane apForPDFView, apMain;
	private TimeSpinner timeFrom, timeTo;
	private List<? extends CommonModelFx> dataList;
	private LineChartService chartService;
	private BarChartBuilder barChartBuilder;
	private ReportService reportService;

	@FXML
	public void initialize() {
		ListDataFx listDataFx = ListDataFx.getInstance();
		ListHarmoFx listHarmoFx = ListHarmoFx.getInstance();
		List<DataFx> dataFxList = Objects.requireNonNull(listDataFx).getDataFxList();
		List<HarmoFx> harmoFxList = Objects.requireNonNull(listHarmoFx).getHarmoFxList();
		apMain.disableProperty().bind(toggleProperty);
		try {
			dataList = CommonUtils.mergeFxModelLists(dataFxList, harmoFxList);

			chartService = new LineChartService();
			reportService = new ReportService();

			addTimeSpinnersToGrid();
			bindDatePickers();
			bindings();
			toggleProperty.set(false);
		} catch (ApplicationException e) {
			toggleProperty.set(true);
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	private void bindings() {
		modelComboBox.getItems().setAll(FXCollections.observableArrayList(AnalyzersModels.values()));
	}

	private void addTimeSpinnersToGrid() {
		timeFrom = new TimeSpinner();
		timeFrom.setId("timeSpinnerFrom");
		timeFrom.maxWidth(Double.MAX_VALUE);
		timeTo = new TimeSpinner();
		timeTo.setId("timeSpinnerTo");
		timeTo.maxWidth(Double.MAX_VALUE);

		timeGrid.add(timeFrom, 0, 2);
		timeGrid.add(timeTo, 1, 2);
	}

	private void bindDatePickers() {
		LocalDateTime startDate = dataList.get(0).getDate();
		LocalDateTime endDate = dataList.get(dataList.size() - 1).getDate();

		restrictDatePicker(dateFrom, startDate.toLocalDate(), endDate.toLocalDate());
		restrictDatePicker(dateTo, startDate.toLocalDate(), endDate.toLocalDate());

		dateFrom.setValue(startDate.toLocalDate());
		dateTo.setValue(endDate.toLocalDate());
	}


	@FXML
	private void generateOnAction() {
		try {
			generateLineCharts();
		} catch (ApplicationException | IOException e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	private void generateLineCharts() throws ApplicationException, IOException {
		List<UniNames> L1 = new ArrayList<>(Arrays.asList(
				PQ_H1_UL1, PQ_H2_UL1, PQ_H3_UL1, PQ_H4_UL1, PQ_H5_UL1, PQ_H6_UL1, PQ_H7_UL1, PQ_H8_UL1, PQ_H9_UL1, PQ_H10_UL1,
				PQ_H11_UL1, PQ_H12_UL1, PQ_H13_UL1, PQ_H14_UL1, PQ_H15_UL1, PQ_H16_UL1, PQ_H17_UL1, PQ_H18_UL1, PQ_H19_UL1, PQ_H20_UL1,
				PQ_H21_UL1, PQ_H22_UL1, PQ_H23_UL1, PQ_H24_UL1, PQ_H25_UL1, PQ_H26_UL1, PQ_H27_UL1, PQ_H28_UL1, PQ_H29_UL1, PQ_H30_UL1,
				PQ_H31_UL1, PQ_H32_UL1, PQ_H33_UL1, PQ_H34_UL1, PQ_H35_UL1, PQ_H36_UL1, PQ_H37_UL1, PQ_H38_UL1, PQ_H39_UL1, PQ_H40_UL1,
				PQ_H41_UL1, PQ_H42_UL1, PQ_H43_UL1, PQ_H44_UL1, PQ_H45_UL1, PQ_H46_UL1, PQ_H47_UL1, PQ_H48_UL1, PQ_H49_UL1, PQ_H50_UL1));
		List<UniNames> L2 = new ArrayList<>(Arrays.asList(
				PQ_H1_UL2, PQ_H2_UL2, PQ_H3_UL2, PQ_H4_UL2, PQ_H5_UL2, PQ_H6_UL2, PQ_H7_UL2, PQ_H8_UL2, PQ_H9_UL2, PQ_H10_UL2,
				PQ_H11_UL2, PQ_H12_UL2, PQ_H13_UL2, PQ_H14_UL2, PQ_H15_UL2, PQ_H16_UL2, PQ_H17_UL2, PQ_H18_UL2, PQ_H19_UL2, PQ_H20_UL2,
				PQ_H21_UL2, PQ_H22_UL2, PQ_H23_UL2, PQ_H24_UL2, PQ_H25_UL2, PQ_H26_UL2, PQ_H27_UL2, PQ_H28_UL2, PQ_H29_UL2, PQ_H30_UL2,
				PQ_H31_UL2, PQ_H32_UL2, PQ_H33_UL2, PQ_H34_UL2, PQ_H35_UL2, PQ_H36_UL2, PQ_H37_UL2, PQ_H38_UL2, PQ_H39_UL2, PQ_H40_UL2,
				PQ_H41_UL2, PQ_H42_UL2, PQ_H43_UL2, PQ_H44_UL2, PQ_H45_UL2, PQ_H46_UL2, PQ_H47_UL2, PQ_H48_UL2, PQ_H49_UL2, PQ_H50_UL2));
		List<UniNames> L3 = new ArrayList<>(Arrays.asList(
				PQ_H1_UL3, PQ_H2_UL3, PQ_H3_UL3, PQ_H4_UL3, PQ_H5_UL3, PQ_H6_UL3, PQ_H7_UL3, PQ_H8_UL3, PQ_H9_UL3, PQ_H10_UL3,
				PQ_H11_UL3, PQ_H12_UL3, PQ_H13_UL3, PQ_H14_UL3, PQ_H15_UL3, PQ_H16_UL3, PQ_H17_UL3, PQ_H18_UL3, PQ_H19_UL3, PQ_H20_UL3,
				PQ_H21_UL3, PQ_H22_UL3, PQ_H23_UL3, PQ_H24_UL3, PQ_H25_UL3, PQ_H26_UL3, PQ_H27_UL3, PQ_H28_UL3, PQ_H29_UL3, PQ_H30_UL3,
				PQ_H31_UL3, PQ_H32_UL3, PQ_H33_UL3, PQ_H34_UL3, PQ_H35_UL3, PQ_H36_UL3, PQ_H37_UL3, PQ_H38_UL3, PQ_H39_UL3, PQ_H40_UL3,
				PQ_H41_UL3, PQ_H42_UL3, PQ_H43_UL3, PQ_H44_UL3, PQ_H45_UL3, PQ_H46_UL3, PQ_H47_UL3, PQ_H48_UL3, PQ_H49_UL3, PQ_H50_UL3));

		LocalDateTime from = LocalDateTime.of(dateFrom.getValue(), timeFrom.getValue());
		LocalDateTime to = LocalDateTime.of(dateTo.getValue(), timeTo.getValue());
		if (from.isBefore(to)) {
			List<Long> allIdByDateBetween = DataManager.findIdByDateBetween(from, to);
			List<CommonDbModel> allByIdBetween = DataManager.findAllByIdBetween(allIdByDateBetween.get(0), allIdByDateBetween.get(allIdByDateBetween.size() - 1));
			final List<Double> avgOfL1 = getAvgOf50HarmonicsOfLine(L1, allByIdBetween);
			final List<Double> maxOfL1 = getMaxOf50HarmonicOfLane(L1, allByIdBetween);
			final List<Double> percentile95OfL1 = get95PercentileOfLane(L1, allByIdBetween);

			barChartBuilder.createNew();
			barChartBuilder.setTitle("Widmo napięcia fazy L1");
			barChartBuilder.setAvgSeries(avgOfL1);
			barChartBuilder.setMaxSeries(maxOfL1);
			barChartBuilder.set95Series(percentile95OfL1);
			final StackedBarChart<Number, Number> result = barChartBuilder.getResult();
			SavingUtils.fastSaveBarChart(result, "wykres_widmo_l1");
		}
		throw new ApplicationException("bad value"); //todo exception communicate

	}

	private List<Double> get95PercentileOfLane(List<UniNames> L, List<CommonDbModel> allByIdBetween) {
		List<Double> percentile95List = new ArrayList<>();
		L.forEach(Hn -> {
			List<Double> allHn = allByIdBetween.stream().map(record -> record.getRecords().get(Hn))
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			Collections.sort(allHn);
			final Double percentile = CommonUtils.percentile(allHn, 95);
			percentile95List.add(percentile);
		});
		return percentile95List;
	}

	private static List<Double> getAvgOf50HarmonicsOfLine(List<UniNames> L, List<CommonDbModel> allByIdBetween) {
		List<Double> avg50n = new ArrayList<>();
		L.forEach(Hn -> {
			List<Double> allHn = allByIdBetween.stream().map(record -> record.getRecords().get(Hn))
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			OptionalDouble average = allHn
					.stream()
					.mapToDouble(a -> a)
					.average();
			avg50n.add(average.getAsDouble());
		});
		return avg50n;
	}

	private static List<Double> getMaxOf50HarmonicOfLane(List<UniNames> L, List<CommonDbModel> allByIdBetween) {
		List<Double> max50n = new ArrayList<>();
		L.forEach(Hn -> {
			List<Double> allHn = allByIdBetween.stream().map(record -> record.getRecords().get(Hn))
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			OptionalDouble average = allHn
					.stream()
					.mapToDouble(a -> a)
					.max();
			max50n.add(average.getAsDouble());
		});
		return max50n;
	}



}
