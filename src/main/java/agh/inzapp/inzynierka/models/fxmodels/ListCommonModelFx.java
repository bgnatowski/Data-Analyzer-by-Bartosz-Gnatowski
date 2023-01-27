package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class ListCommonModelFx {
	private ObservableList<CommonModelFx> modelsFxObservableList;
	private List<CommonModelFx> modelsFx;
	private boolean hasBoth;
	private static volatile ListCommonModelFx instance;

	private ListCommonModelFx() {
		modelsFx = new ArrayList<>();
		modelsFxObservableList = FXCollections.observableArrayList();
		hasBoth = false;
	}

	public static ListCommonModelFx getInstance() throws ApplicationException {
		ListCommonModelFx result = instance;
		if (result != null) {
			return result;
		}
		synchronized (ListCommonModelFx.class) {
			if (instance == null) {
				instance = new ListCommonModelFx();
			}
			return instance;
		}
	}

	public void addModelList(List<CommonModelFx> incomingModels) {
		if (modelsFx.isEmpty()) {
			modelsFx.addAll(incomingModels);
		} else {
			for (CommonModelFx commonModelFx : incomingModels) {
				modelsFx.forEach(model -> {
					if (model.getDate().equals(commonModelFx.getDate())) {
						final ObservableList<UniNames> columnNames = model.getColumnNames();
						columnNames.addAll(commonModelFx.getColumnNames());
						final List<UniNames> newColumnNames = columnNames.stream().distinct().toList();
						model.setColumnNames(FXCollections.observableArrayList(newColumnNames));
						model.getRecords().putAll(commonModelFx.getRecords());
						model.getHarmonics().putAll(commonModelFx.getHarmonics());
					}
				});
			}
		}
		testHasBoth();
		modelsFxObservableList.setAll(modelsFx);
	}

	private void testHasBoth() {
		final CommonModelFx commonModelFx = modelsFx.get(0);
		if(!commonModelFx.getRecords().isEmpty() && !commonModelFx.getHarmonics().isEmpty())
			hasBoth=true;
	}

	public static void reset() {
		instance = null;
	}

	public boolean hasOnlyHarmonics() {
		final CommonModelFx commonModelFx = modelsFx.get(0);
		return commonModelFx.getRecords().isEmpty() && !commonModelFx.getHarmonics().isEmpty();
	}

	public boolean hasOnlyStandard() {
		final CommonModelFx commonModelFx = modelsFx.get(0);
		return !commonModelFx.getRecords().isEmpty() && commonModelFx.getHarmonics().isEmpty();
	}

	public boolean hasBoth() {
		return hasBoth;
	}

	public LocalDateTime getStartDate() {
		if (modelsFxObservableList.isEmpty()) return LocalDateTime.now();
		return modelsFxObservableList.stream().findFirst().orElseThrow().getDate();
	}

	public LocalDateTime getEndDate() {
		if (modelsFxObservableList.isEmpty()) return LocalDateTime.now();
		return modelsFxObservableList.get(modelsFxObservableList.size() - 1).getDate();
	}

	public List<CommonModelFx> getRecordsBetween(LocalDateTime from, LocalDateTime to) throws ApplicationException {
		if (!from.isBefore(to)) throw new ApplicationException(FxmlUtils.getInternalizedPropertyByKey("error.date.out.of.range"));
		if (modelsFxObservableList.isEmpty()) return modelsFxObservableList;
		final long start = from.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		final long end = to.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

		return modelsFxObservableList.stream()
				.filter(model -> {
					final long date = model.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
					return date >= start && date <= end;
				})
				.collect(Collectors.toList());
	}

	public List<UniNames> getColumnNames() {
		if (modelsFxObservableList.isEmpty()) return List.of();
		return modelsFxObservableList.stream().findFirst().orElseThrow().getColumnNames();
	}

	public ObservableList<CommonModelFx> getModelsFxObservableList() {
		return modelsFxObservableList;
	}

	public void setModelsFxObservableList(ObservableList<CommonModelFx> modelsFxObservableList) {
		this.modelsFxObservableList = modelsFxObservableList;
	}

	public String[] getColumStandardNamesArray() {
		final ObservableList<UniNames> columnNames = modelsFx.get(0).getColumnNames();
		final List<UniNames> harmonicsNames = UniNames.getHarmonics();
		final List<UniNames> availableNames = columnNames.stream().filter(uniName -> !harmonicsNames.contains(uniName)).toList();
		List<String> strings = new ArrayList<>();
		strings.add("id");
		availableNames.forEach(uniNames -> strings.add(uniNames.toString()));
		return strings.toArray(new String[0]);
	}

	public String[] getColumHarmonicNamesArray() {
		final ObservableList<UniNames> columnNames = modelsFx.get(0).getColumnNames();
		final List<UniNames> harmonicsNames = new ArrayList<>();
		harmonicsNames.add(UniNames.Flag_E);
		harmonicsNames.add(UniNames.Flag_P);
		harmonicsNames.add(UniNames.Flag_G);
		harmonicsNames.add(UniNames.Flag_T);
		harmonicsNames.add(UniNames.Flag_A);
		harmonicsNames.add(UniNames.Flag);
		harmonicsNames.add(UniNames.Date);
		harmonicsNames.add(UniNames.Time);
		harmonicsNames.addAll(UniNames.getHarmonics());
		final List<UniNames> availableNames = columnNames.stream().filter(harmonicsNames::contains).toList();
		List<String> strings = new ArrayList<>();
		strings.add("id");
		availableNames.forEach(uniNames -> strings.add(uniNames.toString()));
		return strings.toArray(new String[0]);
	}

	public ObservableList<UniNames> getColumStandardNames() {
		final ObservableList<UniNames> columnNames = modelsFx.get(0).getColumnNames();
		final List<UniNames> harmonicsNames = UniNames.getHarmonics();
		final List<UniNames> availableNames = columnNames.stream().filter(uniName -> !harmonicsNames.contains(uniName)).toList();
		return FXCollections.observableArrayList(availableNames);
	}

	public ObservableList<UniNames> getColumHarmonicNames() {
		final ObservableList<UniNames> columnNames = modelsFx.get(0).getColumnNames();
		final List<UniNames> harmonicsNames = new ArrayList<>();
		harmonicsNames.add(UniNames.Flag_E);
		harmonicsNames.add(UniNames.Flag_P);
		harmonicsNames.add(UniNames.Flag_G);
		harmonicsNames.add(UniNames.Flag_T);
		harmonicsNames.add(UniNames.Flag_A);
		harmonicsNames.add(UniNames.Flag);
		harmonicsNames.add(UniNames.Date);
		harmonicsNames.add(UniNames.Time);
		harmonicsNames.addAll(UniNames.getHarmonics());
		final List<UniNames> availableNames = columnNames.stream().filter(harmonicsNames::contains).toList();
		return FXCollections.observableArrayList(availableNames);
	}

	public String getImportedStatistics() {
		//Przydałaby się lista zaimportowanych danych wraz z krótką statystyką:
		int ileKolumn = getColumnNames().size();
		int ileWierszy = modelsFx.size();
		List<Long> ilePustychFlag = calculateEmptyFlags();
		List<Long> ilePustychPltL1 = calculateEmptyPltL1();
		List<Long> ilePustychPltL2 = calculateEmptyPltL2();
		List<Long> ilePustychPltL3 = calculateEmptyPltL3();
		final ObservableList<UniNames> standardColumns = getColumStandardNames();
		final ObservableList<UniNames> harmoColumns = getColumHarmonicNames();
		boolean czyNapiecia = standardColumns.containsAll(UniNames.getNormalEnough());
		boolean czyHarmoniczne = harmoColumns.containsAll(UniNames.getHarmonicsEnough());
		boolean czyCaly = czyHarmoniczne && czyNapiecia;

		return FxmlUtils.getInternalizedPropertyByKey("stat.column") + ileKolumn + "\n" +
				FxmlUtils.getInternalizedPropertyByKey("stat.record") + ileWierszy + "\n" +
				FxmlUtils.getInternalizedPropertyByKey("stat.flags") + ilePustychFlag.get(0) + "/" + ilePustychFlag.get(1) + "\n" +
				FxmlUtils.getInternalizedPropertyByKey("stat.plt1") + ilePustychPltL1.get(0) + "/" + ilePustychPltL1.get(1) + "\n" +
				FxmlUtils.getInternalizedPropertyByKey("stat.plt2") + ilePustychPltL2.get(0) + "/" + ilePustychPltL2.get(1) + "\n" +
				FxmlUtils.getInternalizedPropertyByKey("stat.plt3") + ilePustychPltL3.get(0) + "/" + ilePustychPltL3.get(1) + "\n" +
				FxmlUtils.getInternalizedPropertyByKey("stat.voltage") + (czyNapiecia ? "tak" : "nie") + "\n" +
				FxmlUtils.getInternalizedPropertyByKey("stat.harmo") + (czyHarmoniczne ? "tak" : "nie") + "\n" +
				FxmlUtils.getInternalizedPropertyByKey("stat.all") + (czyCaly ? "tak" : "nie") + "\n";
	}

	private List<Long> calculateEmptyPltL1() {
		final List<Double> plt = modelsFx.stream().map(modelFx -> modelFx.getRecords().get(UniNames.Plt_L1)).toList();
		long pustych = plt.stream().filter(Objects::isNull).count();
		long wszystkich = plt.size();
		return List.of(pustych, wszystkich);
	}

	private List<Long> calculateEmptyPltL2() {
		final List<Double> plt = modelsFx.stream().map(modelFx -> modelFx.getRecords().get(UniNames.Plt_L2)).toList();
		long pustych = plt.stream().filter(Objects::isNull).count();
		long wszystkich = plt.size();
		return List.of(pustych, wszystkich);
	}

	private List<Long> calculateEmptyPltL3() {
		final List<Double> plt = modelsFx.stream().map(modelFx -> modelFx.getRecords().get(UniNames.Plt_L3)).toList();
		long pustych = plt.stream().filter(Objects::isNull).count();
		long wszystkich = plt.size();
		return List.of(pustych, wszystkich);
	}

	private List<Long> calculateEmptyFlags() {
		final List<ObservableMap<UniNames, String>> flagi = modelsFx.stream().map(CommonModelFx::getFlags).toList();
		AtomicLong pustych = new AtomicLong(0L);
		AtomicLong wszystkich = new AtomicLong(0L);
		flagi.forEach(map -> {
			pustych.addAndGet(map.values().stream().filter(String::isBlank).count());
			wszystkich.addAndGet(map.values().size());
		});

		return List.of(pustych.get(), wszystkich.get());
	}

	public String getNormalStatistics() {
		StringBuilder sb = new StringBuilder();
		sb.append("nazwa kolumny / ilość wierszy / maksimum / średnia / minimum\n");
		final List<UniNames> columnNames = getColumnNames();
		columnNames.forEach(column -> {
			switch (column) {
				case Date, Time, Flag, Flag_A, Flag_E, Flag_G, Flag_P, Flag_T -> {
				}
				case THD_L1, THD_L2, THD_L3, THD_12, THD_23, THD_31,
						H1_UL1, H2_UL1, H3_UL1, H4_UL1, H5_UL1, H6_UL1, H7_UL1, H8_UL1, H9_UL1, H10_UL1,
						H11_UL1, H12_UL1, H13_UL1, H14_UL1, H15_UL1, H16_UL1, H17_UL1, H18_UL1, H19_UL1, H20_UL1,
						H21_UL1, H22_UL1, H23_UL1, H24_UL1, H25_UL1, H26_UL1, H27_UL1, H28_UL1, H29_UL1, H30_UL1,
						H31_UL1, H32_UL1, H33_UL1, H34_UL1, H35_UL1, H36_UL1, H37_UL1, H38_UL1, H39_UL1, H40_UL1,
						H41_UL1, H42_UL1, H43_UL1, H44_UL1, H45_UL1, H46_UL1, H47_UL1, H48_UL1, H49_UL1, H50_UL1,
						H1_UL2, H2_UL2, H3_UL2, H4_UL2, H5_UL2, H6_UL2, H7_UL2, H8_UL2, H9_UL2, H10_UL2,
						H11_UL2, H12_UL2, H13_UL2, H14_UL2, H15_UL2, H16_UL2, H17_UL2, H18_UL2, H19_UL2, H20_UL2,
						H21_UL2, H22_UL2, H23_UL2, H24_UL2, H25_UL2, H26_UL2, H27_UL2, H28_UL2, H29_UL2, H30_UL2,
						H31_UL2, H32_UL2, H33_UL2, H34_UL2, H35_UL2, H36_UL2, H37_UL2, H38_UL2, H39_UL2, H40_UL2,
						H41_UL2, H42_UL2, H43_UL2, H44_UL2, H45_UL2, H46_UL2, H47_UL2, H48_UL2, H49_UL2, H50_UL2,
						H1_UL3, H2_UL3, H3_UL3, H4_UL3, H5_UL3, H6_UL3, H7_UL3, H8_UL3, H9_UL3, H10_UL3,
						H11_UL3, H12_UL3, H13_UL3, H14_UL3, H15_UL3, H16_UL3, H17_UL3, H18_UL3, H19_UL3, H20_UL3,
						H21_UL3, H22_UL3, H23_UL3, H24_UL3, H25_UL3, H26_UL3, H27_UL3, H28_UL3, H29_UL3, H30_UL3,
						H31_UL3, H32_UL3, H33_UL3, H34_UL3, H35_UL3, H36_UL3, H37_UL3, H38_UL3, H39_UL3, H40_UL3,
						H41_UL3, H42_UL3, H43_UL3, H44_UL3, H45_UL3, H46_UL3, H47_UL3, H48_UL3, H49_UL3, H50_UL3 -> {}
				default -> {
					sb.append(column).append(" ").append(column.getUnit()).append(" / ");
					final List<Double> columnRecords = modelsFx.stream()
							.map(model -> model.getRecords().get(column))
							.filter(Objects::nonNull)
							.toList();
					sb.append(columnRecords.size()).append(" / ");
					sb.append(String.format("%.2f",Collections.max(columnRecords))).append(" / ");

					OptionalDouble average = columnRecords
							.stream()
							.mapToDouble(a -> a)
							.average();

					sb.append(String.format("%.2f", average.getAsDouble())).append(" / ");
					sb.append(String.format("%.2f",Collections.min(columnRecords))).append("\n");
				}
			}
		});
		return sb.toString();
	}

	public String getHarmoStatistics() {
		StringBuilder sb = new StringBuilder();
		sb.append("nazwa kolumny / ilość wierszy / maksimum / średnia / minimum\n");
		final List<UniNames> columnNames = getColumnNames();
		columnNames.forEach(column -> {
			switch (column) {
				case Date, Time, Flag, Flag_A, Flag_E, Flag_G, Flag_P, Flag_T -> {
				}
				case THD_L1, THD_L2, THD_L3, THD_12, THD_23, THD_31,
						H1_UL1, H2_UL1, H3_UL1, H4_UL1, H5_UL1, H6_UL1, H7_UL1, H8_UL1, H9_UL1, H10_UL1,
						H11_UL1, H12_UL1, H13_UL1, H14_UL1, H15_UL1, H16_UL1, H17_UL1, H18_UL1, H19_UL1, H20_UL1,
						H21_UL1, H22_UL1, H23_UL1, H24_UL1, H25_UL1, H26_UL1, H27_UL1, H28_UL1, H29_UL1, H30_UL1,
						H31_UL1, H32_UL1, H33_UL1, H34_UL1, H35_UL1, H36_UL1, H37_UL1, H38_UL1, H39_UL1, H40_UL1,
						H41_UL1, H42_UL1, H43_UL1, H44_UL1, H45_UL1, H46_UL1, H47_UL1, H48_UL1, H49_UL1, H50_UL1,
						H1_UL2, H2_UL2, H3_UL2, H4_UL2, H5_UL2, H6_UL2, H7_UL2, H8_UL2, H9_UL2, H10_UL2,
						H11_UL2, H12_UL2, H13_UL2, H14_UL2, H15_UL2, H16_UL2, H17_UL2, H18_UL2, H19_UL2, H20_UL2,
						H21_UL2, H22_UL2, H23_UL2, H24_UL2, H25_UL2, H26_UL2, H27_UL2, H28_UL2, H29_UL2, H30_UL2,
						H31_UL2, H32_UL2, H33_UL2, H34_UL2, H35_UL2, H36_UL2, H37_UL2, H38_UL2, H39_UL2, H40_UL2,
						H41_UL2, H42_UL2, H43_UL2, H44_UL2, H45_UL2, H46_UL2, H47_UL2, H48_UL2, H49_UL2, H50_UL2,
						H1_UL3, H2_UL3, H3_UL3, H4_UL3, H5_UL3, H6_UL3, H7_UL3, H8_UL3, H9_UL3, H10_UL3,
						H11_UL3, H12_UL3, H13_UL3, H14_UL3, H15_UL3, H16_UL3, H17_UL3, H18_UL3, H19_UL3, H20_UL3,
						H21_UL3, H22_UL3, H23_UL3, H24_UL3, H25_UL3, H26_UL3, H27_UL3, H28_UL3, H29_UL3, H30_UL3,
						H31_UL3, H32_UL3, H33_UL3, H34_UL3, H35_UL3, H36_UL3, H37_UL3, H38_UL3, H39_UL3, H40_UL3,
						H41_UL3, H42_UL3, H43_UL3, H44_UL3, H45_UL3, H46_UL3, H47_UL3, H48_UL3, H49_UL3, H50_UL3 ->
				{
					sb.append(column).append(" ").append(column.getUnit()).append(" / ");
					final List<Double> columnRecords = modelsFx.stream()
							.map(model -> model.getHarmonics().get(column))
							.filter(Objects::nonNull)
							.toList();
					sb.append(columnRecords.size()).append(" / ");
					sb.append(String.format("%.2f",Collections.max(columnRecords))).append(" / ");

					OptionalDouble average = columnRecords
							.stream()
							.mapToDouble(a -> a)
							.average();

					sb.append(String.format("%.2f", average.getAsDouble())).append(" / ");
					sb.append(String.format("%.2f", Collections.min(columnRecords))).append("\n");
				}
				default -> {}
			}
		});
		return sb.toString();
	}
}
