package agh.inzapp.inzynierka.directors;

import agh.inzapp.inzynierka.builders.BarChartBuilder;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static agh.inzapp.inzynierka.models.enums.FXMLNames.STANDALONE_CHART_PANE;
import static agh.inzapp.inzynierka.models.enums.UniNames.getPowerLineHarmonicNames;
@Component
public class ReportBarChartDirector {
	private static final int HOW_MANY_POWERLINES = 3;
	private final BarChartBuilder builder;

	public ReportBarChartDirector() {
		builder = new BarChartBuilder();
	}

	public void createHarmonicsBarCharts(List<CommonModelFx> collect) throws IOException {
		for (int powerline = 1; powerline <= HOW_MANY_POWERLINES; powerline++) {
			AnchorPane barGraphPane = (AnchorPane) FxmlUtils.fxmlLoad(STANDALONE_CHART_PANE);
			final List<UniNames> powerLineHarmonicNames = getPowerLineHarmonicNames(powerline);
			int howMany = getHowManyHarmonicAreInModels(powerLineHarmonicNames, collect.get(0));
			builder.createNew(howMany);
			builder.setTitle("Widmo napięcia fazy L"+powerline);
			builder.setYData(getMaxOf50HarmonicOfLane(powerLineHarmonicNames, collect), "max");
			builder.setYData(get95PercentileOfLane(powerLineHarmonicNames, collect), "95%");
			builder.setYData(getAvgOf50HarmonicsOfLine(powerLineHarmonicNames, collect), "avg");
			builder.updateSeries();
			barGraphPane.getChildren().add(builder.getResult());
			SavingUtils.fastSaveChart(barGraphPane, "wykres_widmo_l" + powerline);
		}
	}

	private int getHowManyHarmonicAreInModels(List<UniNames> L, CommonModelFx model) {
		return (int) L.stream().filter(Hn -> model.getHarmonics().containsKey(Hn)).count() + 1;
	}

	private List<Double> get95PercentileOfLane(List<UniNames> L, List<CommonModelFx> allByIdBetween) {
		List<Double> percentile95List = new ArrayList<>();
		L.forEach(Hn -> {
			List<Double> allHn = allByIdBetween.stream().map(record -> record.getHarmonics().get(Hn))
					.filter(Objects::nonNull).sorted().collect(Collectors.toList());
			final Double percentile = CommonUtils.percentile(allHn, 95);
			percentile95List.add(percentile);
		});
		return percentile95List;
	}

	private List<Double> getAvgOf50HarmonicsOfLine(List<UniNames> L, List<CommonModelFx> allByIdBetween) {
		List<Double> avg50n = new ArrayList<>();
		L.forEach(Hn -> {
			List<Double> allHn = allByIdBetween.stream().map(record -> record.getHarmonics().get(Hn))
					.filter(Objects::nonNull)
					.toList();
			OptionalDouble average = allHn
					.stream()
					.mapToDouble(a -> a)
					.average();
			if(average.isPresent())
				avg50n.add(average.getAsDouble());
		});
		return avg50n;
	}

	private List<Double> getMaxOf50HarmonicOfLane(List<UniNames> L, List<CommonModelFx> allByIdBetween) {
		List<Double> max50n = new ArrayList<>();
		L.forEach(Hn -> {
			List<Double> allHn = allByIdBetween.stream().map(record -> record.getHarmonics().get(Hn))
					.filter(Objects::nonNull)
					.toList();
			OptionalDouble average = allHn
					.stream()
					.mapToDouble(a -> a)
					.max();
			if(average.isPresent())
				max50n.add(average.getAsDouble());
		});
		return max50n;
	}


}
