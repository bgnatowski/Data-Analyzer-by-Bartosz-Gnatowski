package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.utils.parsers.PQParser;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import agh.inzapp.inzynierka.models.fxmodels.PQHarmonicsFx;
import agh.inzapp.inzynierka.utils.DialogUtils;
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

public class CSVImportPQHarmonics implements CSVStrategy {
	private List<HarmoFx> dataModels;
	@Override
	public List<? extends CommonModelFx> importCSVFile(String... path) throws ApplicationException {
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
				PQHarmonicsFx model = new PQHarmonicsFx();

				if (allRecords.contains("")){ //bez tego wczytywało +1 wartość
					break;
				}
				if (!isFirstLineRead) {
					columnsNames.addAll(PQParser.parseHarmonicsNames(allRecords));
					isFirstLineRead = true;
				} else {
					model.init();
					model.setId(++id);
					model.setColumnHarmonicNames(FXCollections.observableArrayList(columnsNames));
					setDataInModel(allRecords, model);
					dataModels.add(model);
				}
			}
		} catch (IOException | CsvValidationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	private void setDataInModel(List<String> recordsList, PQHarmonicsFx model) {
		Stream.of(UniNames.values()).forEach(unitaryName ->{
			Integer columnID = null;
			if (model.getColumnHarmonicNames().contains(unitaryName)){
				columnID = (model.getColumnHarmonicNames().indexOf(unitaryName));
			}
			if(columnID != null){
				switch (unitaryName){
					case Date -> {
						try {
							model.setDate(PQParser.parseDate(recordsList.get(columnID)));
						} catch (ApplicationException e) {
							DialogUtils.errorDialog(e.getMessage());
						}
					}
					case Time -> model.setTime(PQParser.parseTime(recordsList.get(columnID)));
					case Flag -> {
						Map<UniNames, String> flags = model.getFlags();
						flags.put(unitaryName, PQParser.parseFlag(recordsList.get(columnID)));
						model.setFlags(FXCollections.observableMap(flags));
					}
					case PQ_THD_12, PQ_THD_23, PQ_THD_31,PQ_THD_L1, PQ_THD_L2, PQ_THD_L3 -> {
						if(columnID != null){
							Map<UniNames, Double> thdMap = model.getThd();
							try {
								thdMap.put(unitaryName, PQParser.parseDouble(recordsList.get(columnID)));
								model.setThd(FXCollections.observableMap(thdMap));
							} catch (ParseException e) {
								DialogUtils.errorDialog(e.getMessage());
							}
						}

					}
					default -> {
						if(columnID != null){ //sprawdza, czy w odczytanym csv mamy kolumnę o takiej nazwie
							Map<UniNames, Double> harmonicsMap = model.getHarmonics();
							try {
								harmonicsMap.put(unitaryName, PQParser.parseDouble(recordsList.get(columnID)));
								model.setHarmonics(FXCollections.observableMap(harmonicsMap));
							} catch (ParseException e) {
								DialogUtils.errorDialog(e.getMessage());
							}
						}
					}
				}

			}

		});
	}
}
