package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import agh.inzapp.inzynierka.models.fxmodels.SonelHarmonicFx;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class CSVImportSonelHarmonics implements CSVStrategy {
	private List<HarmoFx> dataModels;
	private static final int BLANK_COLUMNS = 2;
	private static final int SKIP_INFO_LINES = 11;
	@Override
	public List<? extends CommonModelFx> importCSVFile(String path) throws ApplicationException {
		dataModels = new ArrayList<>();
		readFile(path);
		return dataModels;
	}
	private void readFile(String path) throws ApplicationException {
		try (Reader reader = new FileReader(path);
			 CSVReader csvReader = new CSVReaderBuilder(reader)
					 .withSkipLines(SKIP_INFO_LINES)
					 .withCSVParser(parser)
					 .build()
		) {
			List<UniNames> columnsNames = new ArrayList<>();
			String[] oneLineValues;
			boolean isFirstLineRead = false;
			long id = 0L;

			while ((oneLineValues = csvReader.readNext()) != null) {
				List<String> allRecords = Arrays.asList(oneLineValues);
				SonelHarmonicFx model = new SonelHarmonicFx();

				if (!isFirstLineRead) {
					columnsNames.addAll(SonelParser.parseHarmonicsNames(allRecords));
					isFirstLineRead = true;
				} else {
					model.init();
					model.setId(++id);
					model.setColumnNames(FXCollections.observableArrayList(columnsNames));
					setDataInModel(allRecords, model);
					dataModels.add(model);
				}
			}
		} catch (IOException | CsvValidationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	private void setDataInModel(List<String> recordsList, SonelHarmonicFx model) {
		Map<UniNames, Double> harmonicsMap = model.getRecords();
		AtomicReference<LocalDate> localDate = new AtomicReference<>();
		AtomicReference<LocalTime> localTime = new AtomicReference<>();

		Stream.of(UniNames.values()).forEach(unitaryName -> {
			Long columnID = null;
			if (model.getColumnNames().contains(unitaryName)) {
				columnID = Long.valueOf(model.getColumnNames().indexOf(unitaryName)) + BLANK_COLUMNS;
			}
			if (columnID != null) {
				final String stringRecord = recordsList.get(Math.toIntExact(columnID));
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
