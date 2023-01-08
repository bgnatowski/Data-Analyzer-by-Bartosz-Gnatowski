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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static agh.inzapp.inzynierka.models.enums.UniNames.*;

@Component
public abstract class CSVImportCommon {
	private static final int BLANK_COLUMNS_SONEL = 2;
	protected CSVParser parser = new CSVParserBuilder().withSeparator(';').withQuoteChar('\'').withIgnoreQuotations(false).build();
	protected List<List<String>> allRecordsList = new ArrayList<>();
	protected List<UniNames> columnsNames = new ArrayList<>();

	private static List<Double> pst1 = new ArrayList<>();
	private static List<Double> pst2 = new ArrayList<>();
	private static List<Double> pst3 = new ArrayList<>();

	abstract protected void readFile(String path) throws ApplicationException;

	abstract protected void saveModels();

	protected void setDataInPQModel(List<String> recordsList, CommonModelFx model) throws ApplicationException {
		Map<UniNames, Double> modelRecords = new LinkedHashMap<>();
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
		if(modelRecords.keySet().size() < 8) throw new ApplicationException(FxmlUtils.getInternalizedPropertyByKey("error.parse.model"));
		model.setDate(LocalDateTime.of(localDate.get(), localTime.get()));
		model.setRecords(FXCollections.observableMap(modelRecords));
	}

	protected void setDataInSonelModel(List<String> recordsList, CommonModelFx model) throws ApplicationException {
		Map<UniNames, Double> modelRecords = new LinkedHashMap<>();
		AtomicReference<LocalDate> localDate = new AtomicReference<>();
		AtomicReference<LocalTime> localTime = new AtomicReference<>();

		AtomicReference<Double> h1_l1 = new AtomicReference<>();
		AtomicReference<Double> h1_l2 = new AtomicReference<>();
		AtomicReference<Double> h1_l3 = new AtomicReference<>();

		final List<UniNames> l1HarmoNames = getL1HarmoNames();
		final List<UniNames> l2HarmoNames = getL2HarmoNames();
		final List<UniNames> l3HarmoNames = getL3HarmoNames();

		Stream.of(values()).forEach(unitaryName -> {
			if (model.getColumnNames().contains(unitaryName)) {
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
							modelRecords.put(unitaryName, 100d);
						} catch (ParseException e) {
							DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
						}
					}
					case H1_UL2 -> {
						try {
							h1_l2.set(SonelParser.parseDouble(stringRecord));
							modelRecords.put(unitaryName, 100d);
						} catch (ParseException e) {
							DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
						}
					}
					case H1_UL3 -> {
						try {
							h1_l3.set(SonelParser.parseDouble(stringRecord));
							modelRecords.put(unitaryName, 100d);
						} catch (ParseException e) {
							DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
						}
					}
					default -> {
						if (stringRecord.isEmpty()) {
							modelRecords.put(unitaryName, null);
						} else {
							try {
								final double aDouble = SonelParser.parseDouble(stringRecord);
								if (l1HarmoNames.contains(unitaryName))
									modelRecords.put(unitaryName, (aDouble / h1_l1.get()) * 100);
								else if (l2HarmoNames.contains(unitaryName))
									modelRecords.put(unitaryName, (aDouble / h1_l2.get()) * 100);
								else if (l3HarmoNames.contains(unitaryName))
									modelRecords.put(unitaryName, (aDouble / h1_l3.get()) * 100);
								else modelRecords.put(unitaryName, aDouble);
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
		if(modelRecords.keySet().size() < 8) throw new ApplicationException(FxmlUtils.getInternalizedPropertyByKey("error.parse.model"));
		model.setDate(LocalDateTime.of(localDate.get(), localTime.get()));
		model.setRecords(FXCollections.observableMap(modelRecords));
	}
}
