package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.modelObj.BaseDataModelObj;
import agh.inzapp.inzynierka.models.modelObj.WinPQDataObj;
import agh.inzapp.inzynierka.utils.converters.WinPQParser;
import agh.inzapp.inzynierka.utils.enums.UnitaryNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

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

public class CSVFromWinPQ implements CSVStrategy {
	private WinPQDataObj model;
	protected List<BaseDataModelObj> dataModels;

	@Override
	public List<BaseDataModelObj> importCSVFile(String... path) throws ApplicationException {
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
			Map<UnitaryNames, Integer> columnsNames = new TreeMap<>();
			String[] oneLineValues;
			boolean isFirstLineRead = false;

			while ((oneLineValues = csvReader.readNext()) != null) {
				List<String> recordsList = Arrays.asList(oneLineValues);
				if (recordsList.contains("")){
					break;
				}

				this.model = new WinPQDataObj();

				if (!isFirstLineRead) {
					columnsNames.putAll(WinPQParser.parseNames(recordsList));
					isFirstLineRead = true;
				} else {
					this.model.setColumnsNames(columnsNames);
					setDataInModel(recordsList, this.model);
					dataModels.add(this.model);
				}
			}
		} catch (IOException | CsvValidationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}
	protected void setDataInModel(List<String> recordsList, WinPQDataObj model) {
		AtomicReference<LocalDate> date = new AtomicReference<>();
		AtomicReference<LocalTime> time = new AtomicReference<>();

		Stream.of(UnitaryNames.values()).forEach(unitaryName ->{
			Integer columnID = model.getColumnsNames().get(unitaryName);
			if(unitaryName.equals(UnitaryNames.Date))
				date.set(WinPQParser.parseDate(recordsList.get(columnID)));
			else if(unitaryName.equals(UnitaryNames.Time)){
				time.set(WinPQParser.parseTime(recordsList.get(columnID)));
				model.setLocalDateTime(LocalDateTime.of(date.get(), time.get()));
			}
			else if(unitaryName.equals(UnitaryNames.Flag))
				model.setFlags(WinPQParser.parseFlag(recordsList.get(columnID)));
			else if(columnID != null){ //sprawdza, czy w odczytanym csv mamy kolumnę o takiej nazwie
				Map<UnitaryNames, Double> records = model.getRecords();
				try {
					String optionalDouble = recordsList.get(columnID);
					if (optionalDouble.equals(" ")){
						records.put(unitaryName, null);
					} else {
						records.put(unitaryName, WinPQParser.parseDouble(optionalDouble, unitaryName));
					}
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				model.setRecords(records);
			}
		});
	}
}
