package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import agh.inzapp.inzynierka.models.fxmodels.PQNormalFx;
import agh.inzapp.inzynierka.utils.parsers.PQParser;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import agh.inzapp.inzynierka.utils.DialogUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Stream;

@Component
public class CSVImportPQ implements CSVStrategy {
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
					 .withSkipLines(0)
					 .withCSVParser(parser)
					 .build()
		) {
			List<UniNames> columnsNames = new ArrayList<>();
			String[] oneLineValues;
			boolean isFirstLineRead = false;
			long id = 0L;

			while ((oneLineValues = csvReader.readNext()) != null) {
				List<String> allRecords = Arrays.asList(oneLineValues);
				PQNormalFx model = new PQNormalFx();

				if (allRecords.contains("")) { //bez tego wczytywało +1 wartość
					break;
				}
				if (!isFirstLineRead) {
					columnsNames.addAll(PQParser.parseNames(allRecords));
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

	private void setDataInModel(List<String> recordsList, PQNormalFx model) {
		Map<UniNames, Double> modelRecords = model.getRecords();

		Stream.of(UniNames.values()).forEach(unitaryName -> {
			Long columnID = null;
			if (model.getColumnNames().contains(unitaryName)) {
				columnID = Long.valueOf(model.getColumnNames().indexOf(unitaryName));
			}
			if (columnID != null) {
				final String stringRecord = recordsList.get(Math.toIntExact(columnID));
				switch (unitaryName) {
					case Date -> {
						try {
							model.setDate(PQParser.parseDate(stringRecord));
						} catch (ApplicationException e) {
							DialogUtils.errorDialog(e.getMessage());
						}
					}
					case Time -> model.setTime(PQParser.parseTime(stringRecord));
					case Flag -> {
						Map<UniNames, String> flags = model.getFlags();
						flags.put(unitaryName, PQParser.parseFlag(stringRecord));
						model.setFlags(FXCollections.observableMap(flags));
					}
					default -> {
						String optionalDouble = stringRecord;
						if (optionalDouble.equals(" ")) {
							modelRecords.put(unitaryName, 0.0);
						} else {
							try {
								modelRecords.put(unitaryName, PQParser.parseDouble(optionalDouble, unitaryName));
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
