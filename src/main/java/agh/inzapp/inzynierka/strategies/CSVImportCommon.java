package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import agh.inzapp.inzynierka.utils.parsers.PQParser;
import agh.inzapp.inzynierka.utils.parsers.SonelParser;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import javafx.collections.FXCollections;

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

public abstract class CSVImportCommon {
	private static final int BLANK_COLUMNS_SONEL = 2;
	protected CSVParser parser = new CSVParserBuilder().withSeparator(';').withQuoteChar('\'').withIgnoreQuotations(false).build();
	protected List<List<String>> allRecordsList = new ArrayList<>();
	protected List<UniNames> columnsNames = new ArrayList<>();

	abstract protected void readFile(String path) throws ApplicationException;

	abstract protected void saveModels();

	protected void setDataInPQModel(List<String> recordsList, CommonModelFx model) {
		Map<UniNames, Double> modelRecords = new LinkedHashMap<>();
		AtomicReference<LocalDate> localDate = new AtomicReference<>();
		AtomicReference<LocalTime> localTime = new AtomicReference<>();

		Stream.of(UniNames.values()).forEach(unitaryName -> {
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
						Map<UniNames, String> flags = new LinkedHashMap<>();
						flags.put(unitaryName, PQParser.parseFlag(stringRecord));
						model.setFlags(FXCollections.observableMap(flags));
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
		model.setDate(LocalDateTime.of(localDate.get(), localTime.get()));
		model.setRecords(FXCollections.observableMap(modelRecords));
	}

	protected void setDataInSonelModel(List<String> recordsList, CommonModelFx model) {
		Map<UniNames, Double> modelRecords = new LinkedHashMap<>();
		AtomicReference<LocalDate> localDate = new AtomicReference<>();
		AtomicReference<LocalTime> localTime = new AtomicReference<>();

		Stream.of(UniNames.values()).forEach(unitaryName -> {
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
						Map<UniNames, String> flags = new LinkedHashMap<>();
						flags.put(unitaryName, SonelParser.parseFlag(stringRecord));
						model.setFlags(FXCollections.observableMap(flags));
					}
					default -> {
						if (stringRecord.isEmpty()) {
							modelRecords.put(unitaryName, null);
						} else {
							try {
								modelRecords.put(unitaryName, SonelParser.parseDouble(stringRecord));
							} catch (ParseException e) {
								DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
							}
						}
					}
				}
			}
		});
		model.setDate(LocalDateTime.of(localDate.get(), localTime.get()));
		model.setRecords(FXCollections.observableMap(modelRecords));
	}
}
