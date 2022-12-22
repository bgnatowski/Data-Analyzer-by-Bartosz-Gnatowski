package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import agh.inzapp.inzynierka.models.fxmodels.SonelHarmonicFx;
import agh.inzapp.inzynierka.models.fxmodels.SonelNormalFx;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.parsers.SonelParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class CSVImportSonelHarmonics extends CSVImportCommon implements CSVStrategy {
	private List<HarmoFx> dataModels;
	private static final int BLANK_COLUMNS = 2;
	private static final int SKIP_INFO_LINES = 11;
	@Override
	public List<? extends CommonModelFx> importCSVFile(String path) throws ApplicationException {
		dataModels = new ArrayList<>();
		readFile(path);
		saveModels();
		return dataModels;
	}

	@Override
	protected void saveModels() {
		AtomicLong id = new AtomicLong(0L);
		allRecordsList.forEach(records ->{
			SonelHarmonicFx model = new SonelHarmonicFx();
			model.setId(id.incrementAndGet());
			model.setColumnNames(FXCollections.observableArrayList(columnsNames));
			setDataInModel(records, model);
			dataModels.add(model);
		});
	}

	@Override
	protected void readFile(String path) throws ApplicationException {
		try (Reader reader = new FileReader(path);
			 CSVReader csvReader = new CSVReaderBuilder(reader)
					 .withSkipLines(SKIP_INFO_LINES)
					 .withCSVParser(parser)
					 .build()
		) {
			String[] oneLineValues;
			boolean isFirstLineRead = false;

			while ((oneLineValues = csvReader.readNext()) != null) {
				List<String> modelLine = Arrays.asList(oneLineValues);

				if (!isFirstLineRead) {
					columnsNames.addAll(SonelParser.parseHarmonicsNames(modelLine));
					isFirstLineRead = true;
				} else {
					allRecordsList.add(modelLine);
				}
			}
		} catch (IOException | CsvValidationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	private void setDataInModel(List<String> recordsList, SonelHarmonicFx model) {
		Map<UniNames, Double> harmonicsMap = new LinkedHashMap<>();
		AtomicReference<LocalDate> localDate = new AtomicReference<>();
		AtomicReference<LocalTime> localTime = new AtomicReference<>();

		Stream.of(UniNames.values()).forEach(unitaryName -> {
			Long columnID = null;
			if (model.getColumnNames().contains(unitaryName)) {
				columnID = Long.valueOf(model.getColumnNames().indexOf(unitaryName)) + BLANK_COLUMNS;
			}
			if (columnID != null) {
				final String stringRecord = recordsList.get(Math.toIntExact(columnID)).trim();
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
						try {
							harmonicsMap.put(unitaryName, SonelParser.parseDouble(stringRecord));
						} catch (ParseException e) {
							DialogUtils.errorDialog(e.getMessage());
						}
					}
				}
			}
		});
		model.setDate(LocalDateTime.of(localDate.get(), localTime.get()));
		model.setRecords(FXCollections.observableMap(harmonicsMap));
	}
}
