package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import agh.inzapp.inzynierka.models.fxmodels.SonelNormalFx;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import agh.inzapp.inzynierka.utils.parsers.PQParser;
import agh.inzapp.inzynierka.utils.parsers.SonelParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

//todo import sonel
public class CSVImportSonel implements CSVStrategy {
	// jakby parser dać jako instancje a nie korzystac jako statyczny
	private List<DataFx> dataModels;

	@Override
	public List<DataFx> importCSVFile(String... path) throws ApplicationException {
		dataModels = new ArrayList<>();
		readFile(path[0]);
		return dataModels;
	}

	private void readFile(String path) throws ApplicationException {
		try (Reader reader = new FileReader(path);
			 CSVReader csvReader = new CSVReaderBuilder(reader)
					 .withSkipLines(11)
					 .withCSVParser(parser)
					 .build()
		) {
			List<UniNames> columnsNames = new ArrayList<>();
			String[] oneLineValues;
			boolean isFirstLineRead = false;
			long id = 0L;

			while ((oneLineValues = csvReader.readNext()) != null) {
				List<String> allRecords = Arrays.asList(oneLineValues);
				SonelNormalFx model = new SonelNormalFx();

//				if (allRecords.contains("")){ //bez tego wczytywało +1 wartość
//					break;
//				}
				if (!isFirstLineRead) {
					columnsNames.addAll(SonelParser.parseNames(allRecords));
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

	private void setDataInModel(List<String> recordsList, SonelNormalFx model) {
		Map<UniNames, Double> modelRecords = model.getRecords();
		Stream.of(UniNames.values()).forEach(unitaryName -> {
			Long columnID = null;
			if (model.getColumnNames().contains(unitaryName)) {
				columnID = Long.valueOf(model.getColumnNames().indexOf(unitaryName)) + 2;
			}
			if (columnID != null) { //sprawdza, czy w odczytanym csv mamy kolumnę o takiej nazwie
				String stringRecord = recordsList.get(Math.toIntExact(columnID)).trim();

				switch (unitaryName) {
					case Date -> {
						try {
							model.setDate(SonelParser.parseDate(stringRecord));
						} catch (ApplicationException e) {
							DialogUtils.errorDialog(e.getMessage());
						}
					}
					case Time -> model.setTime(SonelParser.parseTime(stringRecord));
					case Flag_A, Flag_G, Flag_E, Flag_T, Flag_P -> {
						Map<UniNames, String> flags = model.getFlags();
						flags.put(unitaryName, SonelParser.parseFlag(stringRecord));
						model.setFlags(FXCollections.observableMap(flags));
					}
					default -> {
						String optionalDouble = stringRecord;
						if (optionalDouble.equals("")) {
							modelRecords.put(unitaryName, 0.0);
						} else {
							try {
								modelRecords.put(unitaryName, SonelParser.parseDouble(optionalDouble));
							} catch (ParseException e) {
								DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
							}
						}
					}
				}
			}
		});
		model.setRecords(FXCollections.observableMap(modelRecords));
	}
}
