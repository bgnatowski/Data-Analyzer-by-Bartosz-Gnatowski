package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.builders.BarChartBuilder;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static agh.inzapp.inzynierka.models.enums.UniNames.getPowerLineHarmonicNames;

public class BarChartService {
	private static final int HOW_MANY_POWERLINES = 3;
	private static final int HOW_MANY_SERIES = 3;
	private BarChartBuilder builder;
	public BarChartService() { builder = new BarChartBuilder(); }

	public void createHarmonicsBarCharts(List<CommonModelFx> collect) throws IOException {
		for (int powerline = 1; powerline <= HOW_MANY_POWERLINES; powerline++) {
			AnchorPane barGraphPane = new AnchorPane();
			for (int seriesNumber = 0; seriesNumber < HOW_MANY_SERIES; seriesNumber++) {
				final List<UniNames> powerLineHarmonicNames = getPowerLineHarmonicNames(powerline);
				List<Double> data = new ArrayList<>();
				StringBuilder seriesName = new StringBuilder();
				switch (seriesNumber) {
					case 0 -> {
						data = getMaxOf50HarmonicOfLane(powerLineHarmonicNames, collect);
						seriesName.append("max");
					}
					case 1 -> {
						data = get95PercentileOfLane(powerLineHarmonicNames, collect);
						seriesName.append("95%");
					}
					case 2 -> {
						data = getAvgOf50HarmonicsOfLine(powerLineHarmonicNames, collect);
						seriesName.append("avg");
					}
				}
				builder.createNew();
				builder.setTitle(powerline);
				builder.setSeries(data, seriesName.toString());
				builder.setYAxisBounds(0, 10, 1);
				barGraphPane.getChildren().add(builder.getResult());
			}
			SavingUtils.fastSaveBarChart(barGraphPane, "wykres_widmo_l"+powerline);
		}
	}

	private List<Double> get95PercentileOfLane(List<UniNames> L, List<CommonModelFx> allByIdBetween) {
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

	private List<Double> getAvgOf50HarmonicsOfLine(List<UniNames> L, List<CommonModelFx> allByIdBetween) {
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

	private List<Double> getMaxOf50HarmonicOfLane(List<UniNames> L, List<CommonModelFx> allByIdBetween) {
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
