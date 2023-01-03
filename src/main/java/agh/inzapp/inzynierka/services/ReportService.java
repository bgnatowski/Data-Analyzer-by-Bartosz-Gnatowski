package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.builders.ReportBuilder;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.CommonUtils;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Pictures;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static agh.inzapp.inzynierka.models.enums.UniNames.*;

@NoArgsConstructor
public class ReportService {
	private static final int IMAGES = 10;
	private static final int USER_DATA = 5;
	public static final int POWER_LINES = 3;
	public static final int TABLE_3_ROWS = 25;
	public static final int CONDITIONS = 6;
	private ReportBuilder reportBuilder;
	private List<String> userData;
	private List<CommonModelFx> models;

	public void generateReport(List<CommonModelFx> recordsBetween, List<String> userAdditionalData) throws IOException {
		reportBuilder = new ReportBuilder(new LinkedHashMap<>());
		models = recordsBetween;
		userData = userAdditionalData;

		putAdditional();
		putMeasurementTime();
		putImages();
		calculateTableOne();
		calculateTableTwo();
		calculateTableThree();
		calculateTableFour();
		conclusion();
//		reportBuilder.getReportResult().forEach((s, o) -> System.out.println("{{"+s+"}}" + " " + o.toString()));

		XWPFTemplate.compile("src/main/resources/data/szablon.docx")
				.render(reportBuilder.getReportResult())
				.writeToFile("src/main/resources/data/output.docx");

	}

	private void conclusion() {
		for(int i = 1; i<= CONDITIONS; i++){
			StringBuilder builder = new StringBuilder();
			switch (i){
				case 1 ->{
					if(!isVoltageConditionFulfilled())
						builder.append("nie ");
					builder.append("mieszczą");
				}
				case 2 -> {
					if(!isAsymConditionFulfilled())
						builder.append("nie ");
					builder.append("mieszczą");
				}
				case 5 -> {
					if(!isHarmonicConditionFulfilled())
						builder.append("nie ");
					builder.append("mieszczą");
				}
				case 3 ->{
					if(!isPltConditionFulfilled())
						builder.append("nie ");
					builder.append("zawierają");
				}
				case 4 -> {
					if(!isTHDConditionFulfilled())
						builder.append("nie ");
					builder.append("zawierają");
				}
				default -> {
					if(isQRegistered()) builder.append("rejestrowano pojemnościową moc bierną.");
					else builder.append("moc bierna nie była rejestrowana.");
				}
			}
			reportBuilder.put("warunek"+i, builder.toString());
		}
	}

	private boolean isPltConditionFulfilled() {
		return false;
	}

	private boolean isTHDConditionFulfilled() {
		return false;
	}

	private boolean isHarmonicConditionFulfilled() {
		return false;
	}

	private boolean isAsymConditionFulfilled() {
		return false;
	}

	private boolean isVoltageConditionFulfilled() {
		return false;
	}

	private boolean isQRegistered() {
		AtomicBoolean anyQ = new AtomicBoolean(false);
		UniNames.getQ().forEach(q -> anyQ.set(models.stream().allMatch(model -> model.getRecords().containsKey(q))));
		return anyQ.get();
	}

	private void calculateTableFour() {
		//todo kryterium i zgoda/niezgoda -> tu jeszcze myk czy tworzyć tabele żeby zaznaczyć kolorem
		reportBuilder.put("kryterium_odksztalcenia", "");
		reportBuilder.put("kryterium_harmonicznych", "");

		boolean odkL1 = false;
		boolean odkL2 = false;
		boolean odkL3 = false;
		boolean harmoL1 = false;
		boolean harmoL2 = false;
		boolean harmoL3 = false;

		reportBuilder.put("odkl1zgod", odkL1);
		reportBuilder.put("odkl2zgod", odkL2);
		reportBuilder.put("odkl3zgod", odkL3);
		reportBuilder.put("harml1zgod", harmoL1);
		reportBuilder.put("harml2zgod", harmoL2);
		reportBuilder.put("harml3zgod", harmoL3);
	}

	private void calculateTableThree() {
		List<UniNames> thd = List.of(THD_L1, THD_L2, THD_L3);
		List<UniNames> toRemove = List.of(H1_UL1, H1_UL3, H1_UL3);
		List<Double> allowableTolerance = List.of(8d, 2d, 5d, 1d, 6d, 0.5d, 5d, 0.5d, 1.5d, 0.5d, 3.5d, 0.5d, 3d, 0.5d, 0.5d, 0.5d, 2d, 0.5d, 1.5d, 0.5d, 0.5d, 0.5d, 1.5d, 0.5d, 1.5d);
		for (int i = 1; i <= POWER_LINES; i++) {
			List<UniNames> names = new ArrayList<>(Collections.singleton(thd.get(i-1)));
			names.addAll(UniNames.getPowerLineHarmonicNames(i));
			names.remove(toRemove.get(i-1));

			for(int j = 0; j < TABLE_3_ROWS; j++){
				UniNames name = names.get(j);
				final String percentile95 = CommonUtils.get95Percentile(models, name);
				final String max = CommonUtils.getMax(models, name);
				final String percentageTolerance = CommonUtils.getTolerancePercentage(models, name, allowableTolerance.get(j));

				String tagPer = "p95_"+(j+1)+"l"+i;
				String tagMax = "max_"+(j+1)+"l"+i;
				String tagTol = "tol_"+(j+1)+"l"+i;

				reportBuilder.put(tagPer, percentile95);
				reportBuilder.put(tagMax, max);
				reportBuilder.put(tagTol, percentageTolerance);
			}
		}
	}


	private void calculateTableTwo() {
		//todo kryterium i zgoda/niezgoda -> tu jeszcze myk czy tworzyć tabele żeby zaznaczyć kolorem
		reportBuilder.put("kryterium_rms", "");
		reportBuilder.put("kryterium_asymetrii", "");
		reportBuilder.put("kryterium_plt", "");

		boolean uL1 = false;
		boolean uL2 = false;
		boolean uL3 = false;
		boolean u2u1 = false;
		boolean plt1 = false;
		boolean plt2 = false;
		boolean plt3 = false;

		reportBuilder.put("ul1zgod", uL1);
		reportBuilder.put("ul2zgod", uL2);
		reportBuilder.put("ul3zgod", uL3);
		reportBuilder.put("asyzgod", u2u1);
		reportBuilder.put("pltl1zgod", plt1);
		reportBuilder.put("pltl2zgod", plt2);
		reportBuilder.put("pltl3zgod", plt3);
	}

	private void calculateTableOne() {
		List<UniNames> tableOneNames = List.of(UL1_avg, UL2_avg, UL3_avg, U2_U1_avg, Plt_L1, Plt_L2, Plt_L3);

		tableOneNames.forEach(name -> {
			final String min = CommonUtils.getMin(models, name);
			final String percentile5 = CommonUtils.get5Percentile(models, name);
			final String avg = CommonUtils.getAvg(models, name);
			final String percentile95 = CommonUtils.get95Percentile(models, name);
			final String max = CommonUtils.getMax(models, name);

			switch (name) {
				case UL1_avg -> {
					reportBuilder.put("ul1min", min);
					reportBuilder.put("ul1p5", percentile5);
					reportBuilder.put("ul1sr", avg);
					reportBuilder.put("ul1p95", percentile95);
					reportBuilder.put("ul1max", max);
				}
				case UL2_avg -> {
					reportBuilder.put("ul2min", min);
					reportBuilder.put("ul2p5", percentile5);
					reportBuilder.put("ul2sr", avg);
					reportBuilder.put("ul2p95", percentile95);
					reportBuilder.put("ul2max", max);
				}
				case UL3_avg -> {
					reportBuilder.put("ul3min", min);
					reportBuilder.put("ul3p5", percentile5);
					reportBuilder.put("ul3sr", avg);
					reportBuilder.put("ul3p95", percentile95);
					reportBuilder.put("ul3max", max);
				}
				case U2_U1_avg -> {
					reportBuilder.put("asymin", min);
					reportBuilder.put("asyp5", percentile5);
					reportBuilder.put("asysr", avg);
					reportBuilder.put("asyp95", percentile95);
					reportBuilder.put("asymax", max);
				}
				case Plt_L1 -> {
					reportBuilder.put("pltl1min", min);
					reportBuilder.put("pltl1p5", percentile5);
					reportBuilder.put("pltl1sr", avg);
					reportBuilder.put("pltl1p95", percentile95);
					reportBuilder.put("pltl1max", max);
				}
				case Plt_L2 -> {
					reportBuilder.put("pltl2min", min);
					reportBuilder.put("pltl2p5", percentile5);
					reportBuilder.put("pltl2sr", avg);
					reportBuilder.put("pltl2p95", percentile95);
					reportBuilder.put("pltl2max", max);
				}
				case Plt_L3 -> {
					reportBuilder.put("pltl3min", min);
					reportBuilder.put("pltl3p5", percentile5);
					reportBuilder.put("pltl3sr", avg);
					reportBuilder.put("pltl3p95", percentile95);
					reportBuilder.put("pltl3max", max);
				}
			}
		});
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

		if ((HH == 0 && MM == 30) || (HH != 0 && MM == 0)) {
			formatBuilder.append("%2 minut");
			String pattern = formatBuilder.toString();
			measurementTime = String.format(pattern, DD, HH);
		} else {
			formatBuilder.append("%2d godzin, %2d minut");
			String pattern = formatBuilder.toString();
			measurementTime = String.format(pattern, DD, HH, MM);
		}

		reportBuilder.put("data_start", dateStart.format(DateTimeFormatter.ofPattern("d.MM.yyyy")));
		reportBuilder.put("data_stop", dateEnd.format(DateTimeFormatter.ofPattern("d.MM.yyyy")));
		reportBuilder.put("czas_start", dateEnd.format(DateTimeFormatter.ofPattern("HH:mm")));
		reportBuilder.put("czas_stop", dateEnd.format(DateTimeFormatter.ofPattern("HH:mm")));
		reportBuilder.put("czas_calkowity", measurementTime);
		reportBuilder.put("czas_interwal", interval);
	}

	private void putAdditional() {
		for (int i = 0; i < USER_DATA; i++) {
			reportBuilder.put(getUserDataTag(i), userData.get(i));
		}
	}

	private void putImages() {
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		for (int i = 0; i < IMAGES; i++) {
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
}
