package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.CommonUtils;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import agh.inzapp.inzynierka.utils.parsers.PQParser;
import agh.inzapp.inzynierka.utils.parsers.SonelParser;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import javafx.collections.FXCollections;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static agh.inzapp.inzynierka.models.enums.UniNames.*;

@Component
public abstract class CSVImportCommon {
	private static int BLANK_COLUMNS_SONEL = 0;
	protected CSVParser parser = new CSVParserBuilder().withSeparator(';').withQuoteChar('\'').withIgnoreQuotations(false).build();
	protected List<List<String>> allRecordsList;
	protected List<UniNames> columnsNames;

	protected static List<Double> pst1;
	protected static List<Double> pst2;
	protected static List<Double> pst3;

	abstract protected void readFile(String path) throws ApplicationException;

	abstract protected void saveModels() throws ApplicationException;

	protected void setDataInPQModel(List<String> recordsList, CommonModelFx model) throws ApplicationException {
		Map<UniNames, Double> modelRecords = new LinkedHashMap<>();
		Map<UniNames, Double> modelHarmo = new LinkedHashMap<>();
		AtomicReference<LocalDate> localDate = new AtomicReference<>();
		AtomicReference<LocalTime> localTime = new AtomicReference<>();
		Stream.of(values())
				.filter(uniNames -> !uniNames.equals(Plt_L1))
				.filter(uniNames -> !uniNames.equals(Plt_L2))
				.filter(uniNames -> !uniNames.equals(Plt_L3))
				.forEach(unitaryName -> {
					if (model.getColumnNames().contains(unitaryName)) {
						long columnID = model.getColumnNames().indexOf(unitaryName);
						final String stringRecord = recordsList.get(Math.toIntExact(columnID)).trim();
						switch (unitaryName) {
							case Date -> {
								try {
									localDate.set(PQParser.parseDate(stringRecord));
								} catch (ApplicationException e) {
									DialogUtils.errorDialog(e.getMessage());
								}
							}
							case Time -> localTime.set(PQParser.parseTime(stringRecord));
							case Flag -> {
								Map<UniNames, String> flags = model.getFlags();
								flags.put(unitaryName, PQParser.parseFlag(stringRecord));
								model.setFlags(FXCollections.observableMap(flags));
							}
							case Pst_UL1 -> {
								Double aDouble;
								try {
									aDouble = PQParser.parseDouble(stringRecord, unitaryName);
									modelRecords.put(unitaryName, aDouble);
									pst1.add(aDouble);
									if (pst1.size() == 12) {
										double plt = CommonUtils.calculatePlt(pst1);
										modelRecords.put(Plt_L1, plt);
										pst1.clear();
									} else {
										modelRecords.put(Plt_L1, null);
									}
								} catch (ParseException e) {
									DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
								}
							}
							case Pst_UL2 -> {
								Double aDouble;
								try {
									aDouble = PQParser.parseDouble(stringRecord, unitaryName);
									modelRecords.put(unitaryName, aDouble);
									pst2.add(aDouble);
									if (pst2.size() == 12) {
										double plt = CommonUtils.calculatePlt(pst2);
										modelRecords.put(Plt_L2, plt);
										pst2.clear();
									} else {
										modelRecords.put(Plt_L2, null);
									}
								} catch (ParseException e) {
									DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
								}
							}
							case Pst_UL3 -> {
								Double aDouble;
								try {
									aDouble = PQParser.parseDouble(stringRecord, unitaryName);
									modelRecords.put(unitaryName, aDouble);
									pst3.add(aDouble);
									if (pst3.size() == 12) {
										double plt = CommonUtils.calculatePlt(pst3);
										modelRecords.put(Plt_L3, plt);
										pst3.clear();
									} else {
										modelRecords.put(Plt_L3, null);
									}
								} catch (ParseException e) {
									DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
								}
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
									H41_UL3, H42_UL3, H43_UL3, H44_UL3, H45_UL3, H46_UL3, H47_UL3, H48_UL3, H49_UL3, H50_UL3 -> {
								if (stringRecord.isEmpty()) {
									modelHarmo.put(unitaryName, null);
								} else {
									try {
										modelHarmo.put(unitaryName, PQParser.parseDouble(stringRecord, unitaryName));
									} catch (ParseException e) {
										DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
									}
								}
							}
							default -> {
								if (stringRecord.isEmpty()) {
									modelRecords.put(unitaryName, null);
								} else {
									try {
										modelRecords.put(unitaryName, PQParser.parseDouble(stringRecord, unitaryName));
									} catch (ParseException e) {
										DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
									}
								}
							}
						}
					}
				});
		if(localDate.get() == null || localTime.get() == null){
			throw new ApplicationException(FxmlUtils.getInternalizedPropertyByKey("error.parse.model"));
		}
		model.setDate(LocalDateTime.of(localDate.get(), localTime.get()));
		model.setRecords(FXCollections.observableMap(modelRecords));
		model.setHarmonics(FXCollections.observableMap(modelHarmo));
	}
	protected void setDataInSonelModel(List<String> recordsList, CommonModelFx model) throws ApplicationException {
		Map<UniNames, Double> modelRecords = new LinkedHashMap<>();
		Map<UniNames, Double> modelHarmo = new LinkedHashMap<>();
		AtomicReference<LocalDate> localDate = new AtomicReference<>();
		AtomicReference<LocalTime> localTime = new AtomicReference<>();

		AtomicReference<Double> h1_l1 = new AtomicReference<>();
		AtomicReference<Double> h1_l2 = new AtomicReference<>();
		AtomicReference<Double> h1_l3 = new AtomicReference<>();

		Stream.of(values()).forEach(unitaryName -> {
			if (model.getColumnNames().contains(unitaryName)) {
				if(unitaryName.equals(NONE)){
					return;
				}
				long columnID = model.getColumnNames().indexOf(unitaryName) + BLANK_COLUMNS_SONEL;
				String stringRecord = recordsList.get(Math.toIntExact(columnID)).trim();
				switch (unitaryName) {
					case Date -> {
						try {
							localDate.set(SonelParser.parseDate(stringRecord));
						} catch (ApplicationException e) {
							DialogUtils.errorDialog(e.getMessage());
						}
					}
					case Time -> localTime.set(SonelParser.parseTime(stringRecord));
					case Flag_A, Flag_G, Flag_E, Flag_T, Flag_P -> {
						Map<UniNames, String> flags = model.getFlags();
						flags.put(unitaryName, SonelParser.parseFlag(stringRecord));
						model.setFlags(FXCollections.observableMap(flags));
					}
					case H1_UL1 -> {
						try {
							h1_l1.set(SonelParser.parseDouble(stringRecord));
							modelHarmo.put(unitaryName, 100d);
						} catch (ParseException e) {
							DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
						}
					}
					case H1_UL2 -> {
						try {
							h1_l2.set(SonelParser.parseDouble(stringRecord));
							modelHarmo.put(unitaryName, 100d);
						} catch (ParseException e) {
							DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
						}
					}
					case H1_UL3 -> {
						try {
							h1_l3.set(SonelParser.parseDouble(stringRecord));
							modelHarmo.put(unitaryName, 100d);
						} catch (ParseException e) {
							DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
						}
					}
					case THD_L1, THD_L2, THD_L3, THD_12, THD_23, THD_31 ->{
						if (stringRecord.isEmpty()) {
							modelHarmo.put(unitaryName, null);
						}else{
							try {
								double aDouble = SonelParser.parseDouble(stringRecord);
								modelHarmo.put(unitaryName, aDouble);
							} catch (ParseException e) {
								DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
							}
						}
					}
					case H2_UL1, H3_UL1, H4_UL1, H5_UL1, H6_UL1, H7_UL1, H8_UL1, H9_UL1, H10_UL1,
							H11_UL1, H12_UL1, H13_UL1, H14_UL1, H15_UL1, H16_UL1, H17_UL1, H18_UL1, H19_UL1, H20_UL1,
							H21_UL1, H22_UL1, H23_UL1, H24_UL1, H25_UL1, H26_UL1, H27_UL1, H28_UL1, H29_UL1, H30_UL1,
							H31_UL1, H32_UL1, H33_UL1, H34_UL1, H35_UL1, H36_UL1, H37_UL1, H38_UL1, H39_UL1, H40_UL1,
							H41_UL1, H42_UL1, H43_UL1, H44_UL1, H45_UL1, H46_UL1, H47_UL1, H48_UL1, H49_UL1, H50_UL1 -> {
						if (stringRecord.isEmpty()) {
							modelHarmo.put(unitaryName, null);
						}else{
							try {
								double aDouble = SonelParser.parseDouble(stringRecord);
								double percentage = (aDouble / h1_l1.get()) * 100;
								modelHarmo.put(unitaryName, percentage);
							} catch (ParseException e) {
								DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
							}
						}
					}
					case H2_UL2, H3_UL2, H4_UL2, H5_UL2, H6_UL2, H7_UL2, H8_UL2, H9_UL2, H10_UL2,
							H11_UL2, H12_UL2, H13_UL2, H14_UL2, H15_UL2, H16_UL2, H17_UL2, H18_UL2, H19_UL2, H20_UL2,
							H21_UL2, H22_UL2, H23_UL2, H24_UL2, H25_UL2, H26_UL2, H27_UL2, H28_UL2, H29_UL2, H30_UL2,
							H31_UL2, H32_UL2, H33_UL2, H34_UL2, H35_UL2, H36_UL2, H37_UL2, H38_UL2, H39_UL2, H40_UL2,
							H41_UL2, H42_UL2, H43_UL2, H44_UL2, H45_UL2, H46_UL2, H47_UL2, H48_UL2, H49_UL2, H50_UL2 ->{
						if (stringRecord.isEmpty()) {
							modelHarmo.put(unitaryName, null);
						}else{
							try {
								double aDouble = SonelParser.parseDouble(stringRecord);
								double percentage = (aDouble / h1_l2.get()) * 100;
								modelHarmo.put(unitaryName, percentage);
							} catch (ParseException e) {
								DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
							}
						}
					}
					case H2_UL3, H3_UL3, H4_UL3, H5_UL3, H6_UL3, H7_UL3, H8_UL3, H9_UL3, H10_UL3,
							H11_UL3, H12_UL3, H13_UL3, H14_UL3, H15_UL3, H16_UL3, H17_UL3, H18_UL3, H19_UL3, H20_UL3,
							H21_UL3, H22_UL3, H23_UL3, H24_UL3, H25_UL3, H26_UL3, H27_UL3, H28_UL3, H29_UL3, H30_UL3,
							H31_UL3, H32_UL3, H33_UL3, H34_UL3, H35_UL3, H36_UL3, H37_UL3, H38_UL3, H39_UL3, H40_UL3,
							H41_UL3, H42_UL3, H43_UL3, H44_UL3, H45_UL3, H46_UL3, H47_UL3, H48_UL3, H49_UL3, H50_UL3
							->{
						if (stringRecord.isEmpty()) {
							modelHarmo.put(unitaryName, null);
						}else{
							try {
								double aDouble = SonelParser.parseDouble(stringRecord);
								double percentage = (aDouble / h1_l3.get()) * 100;
								modelHarmo.put(unitaryName, percentage);
							} catch (ParseException e) {
								DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
							}
						}
					}
					default -> {
						if (stringRecord.isEmpty()) {
							modelRecords.put(unitaryName, null);
						} else {
							try {
								final double aDouble = SonelParser.parseDouble(stringRecord);
								modelRecords.put(unitaryName, aDouble);
							} catch (ParseException e) {
								DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
							}
						}
					}
				}
			}
		});
		if(localDate.get() == null || localTime.get() == null){
			throw new ApplicationException(FxmlUtils.getInternalizedPropertyByKey("error.parse.model"));
		}
		model.setDate(LocalDateTime.of(localDate.get(), localTime.get()));
		model.setRecords(FXCollections.observableMap(modelRecords));
		model.setHarmonics(FXCollections.observableMap(modelHarmo));
	}
}
