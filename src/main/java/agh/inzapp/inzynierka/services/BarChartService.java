package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.builders.BarChartBuilder;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static agh.inzapp.inzynierka.models.enums.UniNames.getPowerLineHarmonicNames;
@Component

public class BarChartService {
	private static final int HOW_MANY_POWERLINES = 3;
	private final BarChartBuilder builder;

	public BarChartService() {
		builder = new BarChartBuilder();
	}

	public void createHarmonicsBarCharts(List<CommonModelFx> collect) throws IOException {
		for (int powerline = 1; powerline <= HOW_MANY_POWERLINES; powerline++) {
			AnchorPane barGraphPane = (AnchorPane) FxmlUtils.fxmlLoad("/fxml/ChartAnchorPane.fxml");
			final List<UniNames> powerLineHarmonicNames = getPowerLineHarmonicNames(powerline);
			builder.createNew();
			builder.setTitle("Widmo napięcia fazy L"+powerline);
			builder.setSeries(getMaxOf50HarmonicOfLane(powerLineHarmonicNames, collect), "max");
			builder.setSeries(get95PercentileOfLane(powerLineHarmonicNames, collect), "95%");
			builder.setSeries(getAvgOf50HarmonicsOfLine(powerLineHarmonicNames, collect), "avg");
			builder.updateSeries();
			barGraphPane.getChildren().add(builder.getResult());
			SavingUtils.fastSaveBarChart(barGraphPane, "wykres_widmo_l" + powerline);
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
