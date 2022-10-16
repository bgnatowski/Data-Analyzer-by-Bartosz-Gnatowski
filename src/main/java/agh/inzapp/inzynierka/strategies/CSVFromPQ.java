package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.modelObj.BaseDataObj;
import agh.inzapp.inzynierka.models.modelObj.PQDataObj;
import agh.inzapp.inzynierka.converters.PQParser;
import agh.inzapp.inzynierka.enums.UniNames;
import agh.inzapp.inzynierka.exceptions.ApplicationException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Component
public class CSVFromPQ implements CSVStrategy {
	protected List<BaseDataObj> dataModels;
	@Override
	public List<BaseDataObj> importCSVFile(String... path) throws ApplicationException {
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
			Map<UniNames, Integer> columnsNames = new LinkedHashMap<>();
			String[] oneLineValues;
			boolean isFirstLineRead = false;
			Long id = Long.valueOf(0);

			while ((oneLineValues = csvReader.readNext()) != null) {
				List<String> recordsList = Arrays.asList(oneLineValues);
				if (recordsList.contains("")){
					break;
				}

				PQDataObj model = new PQDataObj();
				model.setId(++id);

				if (!isFirstLineRead) {
					columnsNames.putAll(PQParser.parseNames(recordsList));
					isFirstLineRead = true;
				} else {
					model.setColumnsNamesIndexMap(columnsNames);
					setDataInModel(recordsList, model);
					dataModels.add(model);
//					System.out.println(model.getId());
				}
			}
		} catch (IOException | CsvValidationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}
	protected void setDataInModel(List<String> recordsList, PQDataObj model) {
		AtomicReference<LocalDate> date = new AtomicReference<>();
		AtomicReference<LocalTime> time = new AtomicReference<>();

		Stream.of(UniNames.values()).forEach(unitaryName ->{
			Integer columnID = model.getColumnsNamesIndexMap().get(unitaryName);
			if(unitaryName.equals(UniNames.Date))
				date.set(PQParser.parseDate(recordsList.get(columnID)));
			else if(unitaryName.equals(UniNames.Time)){
				time.set(PQParser.parseTime(recordsList.get(columnID)));
				model.setLocalDateTime(LocalDateTime.of(date.get(), time.get()));
			}
			else if(unitaryName.equals(UniNames.Flag)) {
				Map<UniNames, String> flags = model.getFlags();
				flags.put(unitaryName, PQParser.parseFlag(recordsList.get(columnID)));
				model.setFlags(flags);
			}
			else if(columnID != null){ //sprawdza, czy w odczytanym csv mamy kolumnę o takiej nazwie
				Map<UniNames, Double> records = model.getRecords();
				try {
					String optionalDouble = recordsList.get(columnID);
					if (optionalDouble.equals(" ")){
						records.put(unitaryName, 0.0);
					} else {
						records.put(unitaryName, PQParser.parseDouble(optionalDouble, unitaryName));
					}
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				model.setRecords(records);
			}
		});
	}
}
