package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.builders.LineChartBuilder;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static agh.inzapp.inzynierka.models.enums.UniNames.*;

public class ReportLineChartService {
	private List<CommonModelFx> models;
	private LineChartBuilder lineChartBuilder;

	public ReportLineChartService() {
		this.lineChartBuilder = new LineChartBuilder();
	}
	public void createLineCharts(List<CommonModelFx> recordsBetweenDate) throws ApplicationException, IOException {
		models = recordsBetweenDate;
		if(isTheSameDay()) lineChartBuilder.setTimeTick();
		List<LocalDateTime> xData = models.stream().map(model -> model.getDate()).toList();
		Map<LocalDateTime, Double> xyDataMap;
		for (int i = 0; i < 6; i++) { // 6 wykresów
			lineChartBuilder.createNew();
			lineChartBuilder.setLegendVisible(true);

			AnchorPane ap = new AnchorPane();
			for (int j = 0; j < 3; j++) { // po 3 serie
				UniNames seriesName = getSeriesName(i, j);
				List<Double> yData = getYData(seriesName);

				final Double min = Collections.min(yData);
				final Double max = Collections.max(yData);
				final Double off = max-min;

				xyDataMap = CommonUtils.zipToMap(xData, yData);
				lineChartBuilder.createSeries(xyDataMap, seriesName);
				lineChartBuilder.setYAxisBounds(min-off, max+off, (max-min)/2);
			}
			lineChartBuilder.setYAxisLabel(getAxisYLabel(i));
			lineChartBuilder.setTitle(getTitle(i));
			ap.getChildren().add(lineChartBuilder.getResult());
			SavingUtils.fastSaveChart(ap, getFileNameTitle(i));
		}

		lineChartBuilder.createNew();
		AnchorPane ap = new AnchorPane();
		for(int i = 0; i<2; i++){
			UniNames seriesName = getSeriesName(i);
			List<Double> yData = getYData(seriesName);

			final Double min = Collections.min(yData);
			final Double max = Collections.max(yData);
			final Double off = max-min;

			xyDataMap = CommonUtils.zipToMap(xData, yData);
			lineChartBuilder.createSeries(xyDataMap, seriesName);
			lineChartBuilder.setYAxisBounds(min-off, max+off, (max-min)/2);
		}
		lineChartBuilder.setTitle("Asymetria napięciowa");
		lineChartBuilder.setYAxisLabel("Współczynnik asymetrii [%]");
		ap.getChildren().add(lineChartBuilder.getResult());
		SavingUtils.fastSaveChart(ap, "wykres_asymetria");
	}

	private UniNames getSeriesName(int i) {
		return i==0 ? U2_U1_avg : U0_U1_avg;
	}

	private boolean isTheSameDay() {
		final LocalDateTime first = models.get(0).getDate();
		final LocalDateTime last = models.get(models.size()-1).getDate();
		return CommonUtils.isSameDay(first, last);
	}

	private String getAxisYLabel(int i) {
		switch (i) {
			case 0:
				return "Napięcie [V]";
			case 1:
				return "Współczynnik THD [%]";
			case 2:
				return "Harmoniczna 3 [%]";
			case 3:
				return "Harmoniczna 5 [%]";
			case 4:
				return "Harmoniczna 7 [%]";
			case 5:
				return "Harmoniczna 9 [%]";
			default:
				throw new IllegalStateException("Unexpected value: " + i);
		}
	}

	private String getTitle(int i) {
		switch (i) {
			case 0:
				return "Wartości skuteczne napięć fazowych";
			case 1:
				return "Współczynnik odkształcenia napięcia THD";
			case 2:
				return "Harmoniczna 3";
			case 3:
				return "Harmoniczna 5";
			case 4:
				return "Harmoniczna 7";
			case 5:
				return "Harmoniczna 9";
			default:
				throw new IllegalStateException("Unexpected value: " + i);
		}
	}

	private String getFileNameTitle(int i){
		switch (i) {
			case 0:
				return "wykres_rms";
			case 1:
				return "wykres_thd";
			case 2:
				return "wykres_harmo3";
			case 3:
				return "wykres_harmo5";
			case 4:
				return "wykres_harmo7";
			case 5:
				return "wykres_harmo9";
			default:
				throw new IllegalStateException("Unexpected value: " + i);
		}
	}

	private UniNames getSeriesName(int i, int j) {
		UniNames seriesName = U12_avg;
		switch (i) {
			case 0 -> {
				if (j == 0) seriesName = UL1_avg;
				if (j == 1) seriesName = UL2_avg;
				if (j == 2) seriesName = UL3_avg;
			}
			case 1 -> {
				if (j == 0) seriesName = THD_L1;
				if (j == 1) seriesName = THD_L2;
				if (j == 2) seriesName = THD_L3;
			}
			case 2 -> {
				if (j == 0) seriesName = H3_UL1;
				if (j == 1) seriesName = H3_UL2;
				if (j == 2) seriesName = H3_UL3;
			}
			case 3 -> {
				if (j == 0) seriesName = H5_UL1;
				if (j == 1) seriesName = H5_UL2;
				if (j == 2) seriesName = H5_UL3;
			}
			case 4 -> {
				if (j == 0) seriesName = H7_UL1;
				if (j == 1) seriesName = H7_UL2;
				if (j == 2) seriesName = H7_UL3;
			}
			case 5 -> {
				if (j == 0) seriesName = H9_UL1;
				if (j == 1) seriesName = H9_UL2;
				if (j == 2) seriesName = H9_UL3;
			}
		}
		return seriesName;
	}

	private List<Double> getYData(UniNames uniName) {
		return models.stream().map(model -> model.getRecords().get(uniName)).toList();
	}
}
