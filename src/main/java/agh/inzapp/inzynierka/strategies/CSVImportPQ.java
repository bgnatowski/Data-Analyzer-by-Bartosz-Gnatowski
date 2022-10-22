package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.DataFx;
import agh.inzapp.inzynierka.models.PQNormalFx;
import agh.inzapp.inzynierka.converters.PQParser;
import agh.inzapp.inzynierka.enums.UniNames;
import agh.inzapp.inzynierka.exceptions.ApplicationException;
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
	protected List<DataFx> dataModels;
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

				if (allRecords.contains("")){ //bez tego wczytywało +1 wartość
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
	protected void setDataInModel(List<String> recordsList, PQNormalFx model) {
		Stream.of(UniNames.values()).forEach(unitaryName ->{
			Integer columnID = null;
			if (model.getColumnNames().contains(unitaryName)){
				columnID = model.getColumnNames().indexOf(unitaryName);
			}
			switch (unitaryName){
				case Date -> model.setDate(PQParser.parseDate(recordsList.get(columnID)));
				case Time -> model.setTime(PQParser.parseTime(recordsList.get(columnID)));
				case Flag -> {
					Map<UniNames, String> flags = model.getFlags();
					flags.put(unitaryName, PQParser.parseFlag(recordsList.get(columnID)));
					model.setFlags(FXCollections.observableMap(flags));
				}
				default -> {
					if(columnID != null){ //sprawdza, czy w odczytanym csv mamy kolumnę o takiej nazwie
						Map<UniNames, Double> records = model.getRecords();
						String optionalDouble = recordsList.get(columnID);
						if (optionalDouble.equals(" ")){
							records.put(unitaryName, 0.0);
						} else {
							try {
								records.put(unitaryName, PQParser.parseDouble(optionalDouble, unitaryName));
							} catch (ParseException e) {
								ApplicationException.printDialog(e.getMessage(), e.getClass(), "error.parsingDouble");
							}
						}
						model.setRecords(FXCollections.observableMap(records));
					}
				}
			}

		});
	}
}
