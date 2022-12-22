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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Component
public class CSVImportPQ extends CSVImportCommon implements CSVStrategy {
	private List<DataFx> dataModels;

	@Override
	public List<DataFx> importCSVFile(String path) throws ApplicationException {
		dataModels = new ArrayList<>();
		readFile(path);
		saveModels();
		return dataModels;
	}
	@Override
	protected void saveModels() {
		AtomicLong id = new AtomicLong(0L);
		allRecordsList.forEach(records ->{
			PQNormalFx model = new PQNormalFx();
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
					 .withSkipLines(0)
					 .withCSVParser(parser)
					 .build()
		) {
			String[] oneLineValues;
			boolean isFirstLineRead = false;

			while ((oneLineValues = csvReader.readNext()) != null) {
				List<String> modelLine = Arrays.asList(oneLineValues);
				if (modelLine.contains("")) { //bez tego wczytywało +1 wartość
					break;
				}
				if (!isFirstLineRead) {
					columnsNames.addAll(PQParser.parseNames(modelLine));
					isFirstLineRead = true;
				} else {
					allRecordsList.add(modelLine);
				}
			}
		} catch (IOException | CsvValidationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	private void setDataInModel(List<String> recordsList, PQNormalFx model) {
		Map<UniNames, Double> modelRecords = new LinkedHashMap<>();
		AtomicReference<LocalDate> localDate = new AtomicReference<>();
		AtomicReference<LocalTime> localTime = new AtomicReference<>();
		Stream.of(UniNames.values()).forEach(unitaryName -> {
			if (model.getColumnNames().contains(unitaryName)) {
				Long columnID = Long.valueOf(model.getColumnNames().indexOf(unitaryName));
				final String stringRecord = recordsList.get(Math.toIntExact(columnID));
				switch (unitaryName) {
					case Date -> {
						try {
							localDate.set(PQParser.parseDate(stringRecord));
						} catch (ApplicationException e) {
							DialogUtils.errorDialog(e.getMessage());
						}
					}
					case Time -> {
						localTime.set(PQParser.parseTime(stringRecord));
					}
					case Flag -> {
						Map<UniNames, String> flags = new LinkedHashMap<>();
						flags.put(unitaryName, PQParser.parseFlag(stringRecord));
						model.setFlags(FXCollections.observableMap(flags));
					}
					default -> {
						String optionalDouble = stringRecord;
						if (optionalDouble.equals(" ")) {
							modelRecords.put(unitaryName, null);
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
		model.setDate(LocalDateTime.of(localDate.get(), localTime.get()));
		model.setRecords(FXCollections.observableMap(modelRecords));
	}
}
