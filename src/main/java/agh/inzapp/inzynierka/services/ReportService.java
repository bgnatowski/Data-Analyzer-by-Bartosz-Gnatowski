package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.builders.ReportBuilder;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import com.deepoove.poi.data.Pictures;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static agh.inzapp.inzynierka.models.enums.UniNames.*;

@Component
public class ReportService {
	private final BooleanProperty toggleButtonProperty = new SimpleBooleanProperty(false);
	private static final int IMAGES = 10;
	private static final int USER_DATA = 5;
	private static final int POWER_LINES = 3;
	private static final int TABLE_3_ROWS = 25;
	private static final int CONDITIONS = 8;
	private static final int U_N = 230;
	private static final int ALLOWABLE_ASYM = 2;
	private static final int ALLOWABLE_PLT = 1;
	private static final int ALLOWABLE_THD = 8;
	private static final List<Double> ALLOWABLE_TOLERANCE_HARMONIC = List.of(8d, 2d, 5d, 1d, 6d, 0.5d, 5d, 0.5d, 1.5d, 0.5d, 3.5d, 0.5d, 3d, 0.5d, 0.5d, 0.5d, 2d, 0.5d, 1.5d, 0.5d, 0.5d, 0.5d, 1.5d, 0.5d, 1.5d);
	private final ReportBuilder reportBuilder;
	private List<String> userData;
	private List<CommonModelFx> models;
	private final Map<UniNames, Boolean> conditionsMap = new HashMap<>();

	public ReportService() {
		toggleButtonProperty.setValue(true);
		reportBuilder = new ReportBuilder(new LinkedHashMap<>());
	}

	public String generateReport(List<CommonModelFx> recordsBetween, List<String> userAdditionalData) throws ApplicationException {
		try {
			models = recordsBetween;
			userData = userAdditionalData;

			putAdditional();
			putMeasurementTime();
			putImagesStandard();
			putImagesHarmo();

			calculateTableOne();
			calculateTableTwo();
			calculateTableThree();
			calculateTableFour();

			conclusionStandard(true);
			conclusionHarmo(true);
			conclusionAdded();
			final String reportFilePath = SavingUtils.saveTemporaryReport(reportBuilder.getReportResult());
			toggleButtonProperty.setValue(false);
			return reportFilePath;
		} catch (IOException e) {
			toggleButtonProperty.setValue(true);
		}
		throw new ApplicationException("error.render.doc");
	}

	public String generateReportStandard(List<CommonModelFx> recordsBetween, List<String> userAdditionalData) throws ApplicationException{
		try {
			models = recordsBetween;
			userData = userAdditionalData;

			putAdditional();
			putMeasurementTime();
			putImagesStandard();

			calculateTableOne();
			calculateTableTwo();
			conclusionStandard(true);
			conclusionHarmo(false);
			conclusionAdded();
			final String reportFilePath = SavingUtils.saveTemporaryReport(reportBuilder.getReportResult());
			toggleButtonProperty.setValue(false);
			return reportFilePath;
		} catch (IOException e) {
			toggleButtonProperty.setValue(true);
		}
		throw new ApplicationException("error.render.doc");
	}

	public String generateReportHarmo(List<CommonModelFx> recordsBetween, List<String> userAdditionalData) throws ApplicationException {
		try {
			models = recordsBetween;
			userData = userAdditionalData;

			putAdditional();
			putMeasurementTime();
			putImagesHarmo();

			calculateTableThree();
			calculateTableFour();

			conclusionStandard(false);
			conclusionHarmo(true);
			conclusionAdded();
			final String reportFilePath = SavingUtils.saveTemporaryReport(reportBuilder.getReportResult());
			toggleButtonProperty.setValue(false);
			return reportFilePath;
		} catch (IOException e) {
			toggleButtonProperty.setValue(true);
		}
		throw new ApplicationException("error.render.doc");
	}

	private void conclusionStandard(boolean is) {
		for (int i = 1; i < 4; i++) {
			StringBuilder builder = new StringBuilder();
			if(is){
				switch (i) {
					case 1 -> {
						if (!isVoltageConditionFulfilled())
							builder.append("nie ");
						builder.append("mieszczą się w dopuszczalnym przedziale tolerancji w całym okresie pomiarowym,");
					}
					case 2 -> {
						if (!isAsymConditionFulfilled())
							builder.append("nie ");
						builder.append("mieszczą się w dopuszczalnym przedziale tolerancji w całym okresie pomiarowym,");
					}
					case 3 -> {
						if (!isPltConditionFulfilled())
							builder.append("nie ");
						builder.append("zawierają się w dopuszczalnym przedziale tolerancji w całym okresie pomiarowym,");
					}
				}
			}else{
				builder.append("- brak danych");
			}
			reportBuilder.put("warunek" + i, builder.toString());
		}
	}

	private void conclusionHarmo(boolean is){
		for(int i = 4; i < 6; i++){
			StringBuilder builder = new StringBuilder();
			if(is){
				switch (i){
					case 4 -> {
						if (!isTHDConditionFulfilled())
							builder.append("nie ");
						builder.append("zawierają się w dopuszczalnym przedziale tolerancji przez cały okresu pomiarowy,");
					}
					case 5 -> {
						if (!isHarmonicConditionFulfilled()) {
							builder.append("nie ");
						}
						builder.append("mieszczą się w dopuszczalnych przedziałach tolerancji,");
					}
				}
			}else{
				builder.append("- brak danych");
			}
			reportBuilder.put("warunek" + i, builder.toString());
		}
	}
	private void conclusionAdded(){
		StringBuilder builder = new StringBuilder();

		if (isQRegistered()) builder.append("rejestrowano pojemnościową moc bierną.");
		else builder.append("moc bierna nie była rejestrowana.");
		reportBuilder.put("warunek6", builder.toString());

		builder = new StringBuilder();
		if (isSRegistered()) builder.append("rejestrowano moc pozorną.");
		else builder.append("moc pozorna nie była rejestrowana.");
		reportBuilder.put("warunek7", builder.toString());

		builder = new StringBuilder();
		if (isPRegistered()) builder.append("rejestrowano moc czynną.");
			else builder.append("moc czynna nie była rejestrowana.");
		reportBuilder.put("warunek8", builder.toString());
	}


	private boolean isPltConditionFulfilled() {
		return conditionsMap.get(Plt_L1) && conditionsMap.get(Plt_L2) && conditionsMap.get(Plt_L1);
	}

	private boolean isTHDConditionFulfilled() {
		return conditionsMap.get(THD_L1) && conditionsMap.get(THD_L2) && conditionsMap.get(THD_L3);
	}

	private boolean isHarmonicConditionFulfilled() {
		return conditionsMap.get(H1_UL1) && conditionsMap.get(H1_UL2) && conditionsMap.get(H1_UL3);
	}

	private boolean isAsymConditionFulfilled() {
		return conditionsMap.get(U2_U1_avg);
	}

	private boolean isVoltageConditionFulfilled() {
		return conditionsMap.get(UL1_avg) && conditionsMap.get(UL2_avg) && conditionsMap.get(UL3_avg);
	}

	private boolean isQRegistered() {
		AtomicBoolean anyQ = new AtomicBoolean(false);
		for (UniNames q : getQ()) {
			anyQ.set(models.stream().anyMatch(model -> model.getRecords().containsKey(q)));
			if (anyQ.get()) break;
		}
		return anyQ.get();
	}

	private boolean isSRegistered() {
		AtomicBoolean anyS = new AtomicBoolean(false);
		for (UniNames s : getS()) {
			anyS.set(models.stream().anyMatch(model -> model.getRecords().containsKey(s)));
			if (anyS.get()) break;
		}
		return anyS.get();
	}

	private boolean isPRegistered() {
		AtomicBoolean anyP = new AtomicBoolean(false);
		for (UniNames p : getP()) {
			anyP.set(models.stream().anyMatch(model -> model.getRecords().containsKey(p)));
			if (anyP.get()) break;
		}
		return anyP.get();
	}

	private void calculateTableOne() {
		List<UniNames> tableOneNames = new ArrayList<>(Arrays.asList(UL1_avg, UL2_avg, UL3_avg, U2_U1_avg, Plt_L1, Plt_L2, Plt_L3));

		tableOneNames.forEach(name -> {
			List<Double> column = models.stream().map(record -> record.getRecords().get(name))
					.filter(Objects::nonNull)
					.toList();
			final Double min = CommonUtils.getMin(column, name);
			final Double percentile5 = CommonUtils.get5Percentile(column, name);
			final Double avg = CommonUtils.getAvg(column, name);
			final Double percentile95 = CommonUtils.get95Percentile(column, name);
			final Double max = CommonUtils.getMax(column, name);

			boolean condition = false;
			switch (name) {
				case UL1_avg -> {
					condition = percentile5 > (0.9 * U_N) && percentile95 < (1.1 * U_N);
					reportBuilder.put("ul1min", String.format("%.2f", min));
					reportBuilder.put("ul1p5", String.format("%.2f", percentile5));
					reportBuilder.put("ul1sr", String.format("%.2f", avg));
					reportBuilder.put("ul1p95", String.format("%.2f", percentile95));
					reportBuilder.put("ul1max", String.format("%.2f", max));
				}
				case UL2_avg -> {
					condition = percentile5 > (0.9 * U_N) && percentile95 < (1.1 * U_N);
					reportBuilder.put("ul2min", String.format("%.2f", min));
					reportBuilder.put("ul2p5", String.format("%.2f", percentile5));
					reportBuilder.put("ul2sr", String.format("%.2f", avg));
					reportBuilder.put("ul2p95", String.format("%.2f", percentile95));
					reportBuilder.put("ul2max", String.format("%.2f", max));
				}
				case UL3_avg -> {
					condition = percentile5 > (0.9 * U_N) && percentile95 < (1.1 * U_N);
					reportBuilder.put("ul3min", String.format("%.2f", min));
					reportBuilder.put("ul3p5", String.format("%.2f", percentile5));
					reportBuilder.put("ul3sr", String.format("%.2f", avg));
					reportBuilder.put("ul3p95", String.format("%.2f", percentile95));
					reportBuilder.put("ul3max", String.format("%.2f", max));
				}
				case U2_U1_avg -> {
					condition = percentile95 < ALLOWABLE_ASYM;
					reportBuilder.put("asymin", String.format("%.2f", min));
					reportBuilder.put("asyp5", String.format("%.2f", percentile5));
					reportBuilder.put("asysr", String.format("%.2f", avg));
					reportBuilder.put("asyp95", String.format("%.2f", percentile95));
					reportBuilder.put("asymax", String.format("%.2f", max));
				}
				case Plt_L1 -> {
					condition = percentile95 < ALLOWABLE_PLT;
					reportBuilder.put("pltl1min", String.format("%.2f", min));
					reportBuilder.put("pltl1p5", String.format("%.2f", percentile5));
					reportBuilder.put("pltl1sr", String.format("%.2f", avg));
					reportBuilder.put("pltl1p95", String.format("%.2f", percentile95));
					reportBuilder.put("pltl1max", String.format("%.2f", max));
				}
				case Plt_L2 -> {
					condition = percentile95 < ALLOWABLE_PLT;
					reportBuilder.put("pltl2min", String.format("%.2f", min));
					reportBuilder.put("pltl2p5", String.format("%.2f", percentile5));
					reportBuilder.put("pltl2sr", String.format("%.2f", avg));
					reportBuilder.put("pltl2p95", String.format("%.2f", percentile95));
					reportBuilder.put("pltl2max", String.format("%.2f", max));
				}
				case Plt_L3 -> {
					condition = percentile95 < ALLOWABLE_PLT;
					reportBuilder.put("pltl3min", String.format("%.2f", min));
					reportBuilder.put("pltl3p5", String.format("%.2f", percentile5));
					reportBuilder.put("pltl3sr", String.format("%.2f", avg));
					reportBuilder.put("pltl3p95", String.format("%.2f", percentile95));
					reportBuilder.put("pltl3max", String.format("%.2f", max));
				}
			}
			conditionsMap.put(name, condition);
		});
	}

	private void calculateTableTwo() {
		String uL1 = conditionsMap.get(UL1_avg) ? "TAK" : "NIE";
		String uL2 = conditionsMap.get(UL2_avg) ? "TAK" : "NIE";
		String uL3 = conditionsMap.get(UL3_avg) ? "TAK" : "NIE";
		String u2u1 = conditionsMap.get(U2_U1_avg) ? "TAK" : "NIE";
		String plt1 = conditionsMap.get(Plt_L1) ? "TAK" : "NIE";
		String plt2 = conditionsMap.get(Plt_L2) ? "TAK" : "NIE";
		String plt3 = conditionsMap.get(Plt_L3) ? "TAK" : "NIE";

		reportBuilder.put("ul1zgod", uL1);
		reportBuilder.put("ul2zgod", uL2);
		reportBuilder.put("ul3zgod", uL3);
		reportBuilder.put("asyzgod", u2u1);
		reportBuilder.put("pltl1zgod", plt1);
		reportBuilder.put("pltl2zgod", plt2);
		reportBuilder.put("pltl3zgod", plt3);
	}

	private void calculateTableThree() {
		List<UniNames> thd = List.of(THD_L1, THD_L2, THD_L3);
		List<UniNames> harmonicFirst = List.of(H1_UL1, H1_UL2, H1_UL3);

		// zakładamy, że są zgodne
		thd.forEach(key -> conditionsMap.put(key, true));
		harmonicFirst.forEach(key -> conditionsMap.put(key, true));

		for (int i = 1; i <= POWER_LINES; i++) {
			List<UniNames> names = new ArrayList<>(Collections.singleton(thd.get(i - 1)));
			names.addAll(UniNames.getPowerLineHarmonicNames(i));
			names.remove(harmonicFirst.get(i - 1));

			for (int j = 0; j < TABLE_3_ROWS; j++) {
				UniNames name = names.get(j);
				List<Double> column = models.stream().map(record -> record.getHarmonics().get(name))
						.filter(Objects::nonNull)
						.toList();
				final Double percentile95 = CommonUtils.get95Percentile(column, name);
				final Double max = CommonUtils.getMax(column, name);
				final Double allowableTolerance = ALLOWABLE_TOLERANCE_HARMONIC.get(j);
				final Double percentageTolerance = CommonUtils.getTolerancePercentage(column, name, allowableTolerance);

				// jeśli którakolwiek harmoniczna nie będzie spełniała warunku to cały warunek nie jest spełniony
				boolean conditionHarmo = percentile95 <= allowableTolerance;
				if (!conditionHarmo) conditionsMap.put(harmonicFirst.get(i - 1), false);

				boolean conditionThd = max <= ALLOWABLE_THD;
				if (!conditionThd) conditionsMap.put(thd.get(i - 1), false);

				String tagPer = "p95_" + (j + 1) + "l" + i;
				String tagMax = "max_" + (j + 1) + "l" + i;
				String tagTol = "tol_" + (j + 1) + "l" + i;

				reportBuilder.put(tagPer, String.format("%.2f", percentile95));
				reportBuilder.put(tagMax, String.format("%.2f", max));
				if (percentageTolerance >= 100) reportBuilder.put(tagTol, "100");
				else reportBuilder.put(tagTol, String.format("%.2f", percentageTolerance));
			}
		}
	}

	private void calculateTableFour() {
		String odkL1 = conditionsMap.get(THD_L1) ? "TAK" : "NIE";
		String odkL2 = conditionsMap.get(THD_L2) ? "TAK" : "NIE";
		String odkL3 = conditionsMap.get(THD_L3) ? "TAK" : "NIE";
		String harmoL1 = conditionsMap.get(H1_UL1) ? "TAK" : "NIE";
		String harmoL2 = conditionsMap.get(H1_UL2) ? "TAK" : "NIE";
		String harmoL3 = conditionsMap.get(H1_UL3) ? "TAK" : "NIE";

		reportBuilder.put("thdl1zgod", odkL1);
		reportBuilder.put("thdl2zgod", odkL2);
		reportBuilder.put("thdl3zgod", odkL3);
		reportBuilder.put("harml1zgod", harmoL1);
		reportBuilder.put("harml2zgod", harmoL2);
		reportBuilder.put("harml3zgod", harmoL3);
	}

	private void putMeasurementTime() {
		LocalDateTime dateStart = models.get(0).getDate();
		LocalDateTime dateEnd = models.get(models.size() - 1).getDate();
		final long interval = Duration.between(dateStart, models.get(1).getDate()).toMinutes();

		final Duration duration = Duration.between(dateStart, dateEnd);
		final long DD = duration.toDays();
		final long HH = duration.toHoursPart();
		final long MM = duration.toMinutesPart();

		StringBuilder formatBuilder = new StringBuilder();
		String measurementTime;
		if (DD == 1)
			formatBuilder.append("%2d dzień, ");
		else
			formatBuilder.append("%2d dni, ");

		if (HH == 0 && MM == 30) {
			formatBuilder.append("%2d min.");
			String pattern = formatBuilder.toString();
			measurementTime = String.format(pattern, DD, MM);
		}
		else if(HH != 0 && MM == 0) {
			formatBuilder.append("%2d godz.");
			String pattern = formatBuilder.toString();
			measurementTime = String.format(pattern, DD, HH);
		}
		else {
			formatBuilder.append("%2d godz., %2d min.");
			String pattern = formatBuilder.toString();
			measurementTime = String.format(pattern, DD, HH, MM);
		}

		reportBuilder.put("data_start", dateStart.format(DateTimeFormatter.ofPattern("d.MM.yyyy")));
		reportBuilder.put("data_stop", dateEnd.format(DateTimeFormatter.ofPattern("d.MM.yyyy")));
		reportBuilder.put("czas_start", dateStart.format(DateTimeFormatter.ofPattern("HH:mm")));
		reportBuilder.put("czas_stop", dateEnd.format(DateTimeFormatter.ofPattern("HH:mm")));
		reportBuilder.put("czas_calkowity", measurementTime);
		reportBuilder.put("czas_interwal", interval);
	}

	private void putAdditional() {
		for (int i = 0; i < USER_DATA; i++) {
			reportBuilder.put(getUserDataTag(i), userData.get(i));
		}
	}

	private void putImagesStandard() {
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		for (int i = 0; i < 2; i++) {
			String name = getImageTag(i);
			File chartFile = new File(tempDirectory.getAbsolutePath() + File.separator + name + ".png");
			reportBuilder.put(name, Pictures.ofLocal(chartFile.getAbsolutePath()).sizeInCm(16.09, 6.84).center().create());

		}
	}
	private void putImagesHarmo() {
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		for (int i = 2; i < 10; i++) {
			String name = getImageTag(i);
			File chartFile = new File(tempDirectory.getAbsolutePath() + File.separator + name + ".png");
			reportBuilder.put(name, Pictures.ofLocal(chartFile.getAbsolutePath()).sizeInCm(16.09, 6.84).center().create());
		}
	}

	private String getImageTag(int i) {
		return switch (i) {
			case 0 -> "wykres_rms";
			case 1 -> "wykres_asymetria";
			case 2 -> "wykres_widmo_l1";
			case 3 -> "wykres_widmo_l2";
			case 4 -> "wykres_widmo_l3";
			case 5 -> "wykres_thd";
			case 6 -> "wykres_harmo3";
			case 7 -> "wykres_harmo5";
			case 8 -> "wykres_harmo7";
			case 9 -> "wykres_harmo9";
			default -> throw new IllegalStateException("Unexpected value: " + i);
		};
	}

	private String getUserDataTag(int i) {
		return switch (i) {
			case 0 -> "rozdzielnia";
			case 1 -> "punkt_pomiarowy";
			case 2 -> "analizator";
			case 3 -> "analizator_serial";
			case 4 -> "autor";
			default -> throw new IllegalStateException("Unexpected value: " + i);
		};
	}

	public boolean isToggleButtonProperty() {
		return toggleButtonProperty.get();
	}

	public BooleanProperty toggleButtonPropertyProperty() {
		return toggleButtonProperty;
	}
}
