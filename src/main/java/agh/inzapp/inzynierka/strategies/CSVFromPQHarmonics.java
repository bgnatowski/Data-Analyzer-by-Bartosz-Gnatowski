//package agh.inzapp.inzynierka.strategies;
//
//import agh.inzapp.inzynierka.models.modelObj.BaseDataModelObj;
//import agh.inzapp.inzynierka.models.modelObj.WinPQHarmonicsObjDataObj;
//import agh.inzapp.inzynierka.utils.converters.WinPQParser;
//import agh.inzapp.inzynierka.enums.UnitaryNames;
//import agh.inzapp.inzynierka.exceptions.ApplicationException;
//import com.opencsv.CSVReader;
//import com.opencsv.CSVReaderBuilder;
//import com.opencsv.exceptions.CsvValidationException;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.text.ParseException;
//import java.util.*;
//import java.util.stream.Stream;
//
//public class CSVFromWinPQHarmonics extends CSVFromWinPQ implements CSVStrategy {
//	private final Integer COLUMN_OFFSET = 3;
//	private WinPQHarmonicsObjDataObj model;
//	@Override
//	public List<BaseDataModelObj> importCSVFile(String... path) throws ApplicationException {
//		dataModels = new ArrayList<>();
//		readFile(path[0]);
//		readHarmonicsFile(path[1]);
//
//		return dataModels;
//	}
//	private void readFile(String path) throws ApplicationException {
//		try (Reader reader = new FileReader(path);
//			 CSVReader csvReader = new CSVReaderBuilder(reader)
//					 .withSkipLines(0)
//					 .withCSVParser(parser)
//					 .build()
//		) {
//			String[] values;
//			boolean isFirstRead = false;
//			Map<UnitaryNames, Integer> columnsNames = new TreeMap<>();
//
//			while ((values = csvReader.readNext()) != null) {
//				List<String> recordsList = Arrays.asList(values);
//				this.model = new WinPQHarmonicsObjDataObj();
//				if (!isFirstRead) {
//					columnsNames.putAll(WinPQParser.parseNames(recordsList));
//					isFirstRead = true;
//				} else {
//					this.model.setColumnsNames(columnsNames);
//					if (!recordsList.isEmpty()){
//						setDataInModel(recordsList, this.model);
//					}
//					dataModels.add(this.model);
//				}
//			}
//		} catch (IOException | CsvValidationException e) {
//			throw new ApplicationException(e.getMessage());
//		}
//	}
//
//	private void readHarmonicsFile(String pathHarmonics) throws ApplicationException {
//		try (Reader reader = new FileReader(pathHarmonics);
//			 CSVReader csvReader = new CSVReaderBuilder(reader)
//					 .withSkipLines(0)
//					 .withCSVParser(parser)
//					 .build()
//		) {
//			List<BaseDataModelObj> harmonicDataList = new ArrayList<>();
//			Map<UnitaryNames, Integer> columnsHarmonicsNames = new TreeMap<>();
//			String[] values;
//			boolean isFirstRead = false;
//
//			while ((values = csvReader.readNext()) != null) {
//				List<String> recordsList = Arrays.asList(values);
//				if (recordsList.contains("")){
//					break;
//				}
//
//				Iterator<BaseDataModelObj> iter = dataModels.iterator();
//				if (iter.hasNext()) {
//					this.model = (WinPQHarmonicsObjDataObj) iter.next();
//				}
//
//				if (!isFirstRead) {
//					columnsHarmonicsNames.putAll(WinPQParser.parseHarmonicsNames(recordsList));
//					isFirstRead = true;
//				} else {
//					if (iter.hasNext()) {
//						this.model.setColumnsHarmonicsNames(columnsHarmonicsNames);
//						setHarmonicsDataInModel(recordsList);
//						harmonicDataList.add(this.model);
//					}
//				}
//			}
//			dataModels.clear();
//			dataModels.addAll(harmonicDataList);
//		} catch (IOException | CsvValidationException e) {
//			throw new ApplicationException(e.getMessage());
//		}
//
//	}
//
//	private void setHarmonicsDataInModel(List<String> harmonicsFileDataList) {
//		Stream.of(UnitaryNames.values()).forEach(unitaryName -> {
//			Integer columnHarmonicsID = this.model.getColumnsHarmonicsNames().get(unitaryName);
//			if (unitaryName.equals(UnitaryNames.Date) || unitaryName.equals(UnitaryNames.Time) || unitaryName.equals(UnitaryNames.Flag)) {
//			} else if (columnHarmonicsID != null) { //sprawdza, czy w odczytanym csv mamy kolumnę o takiej nazwie
//				if (unitaryName.name().contains("THD")) {
//					parseThdTooDouble(harmonicsFileDataList, unitaryName, columnHarmonicsID);
//				} else if (unitaryName.name().contains("L1")) {
//					parseL1HarmonicsToDouble(harmonicsFileDataList, unitaryName, columnHarmonicsID);
//				} else if (unitaryName.name().contains("L2")) {
//					parseL2HarmonicsToDouble(harmonicsFileDataList, unitaryName, columnHarmonicsID);
//				} else if (unitaryName.name().contains("L3")) {
//					parseL3HarmonicsToDouble(harmonicsFileDataList, unitaryName, columnHarmonicsID);
//				}
//			}
//		});
//	}
//
//	private void parseThdTooDouble(List<String> harmonicsFileDataList, UnitaryNames unitaryName, Integer columnHarmonicsID) {
//		Map<UnitaryNames, Double> data;
//		data = this.model.getThdData();
//		String optionalDouble = harmonicsFileDataList.get(columnHarmonicsID + COLUMN_OFFSET);
//		try {
//			data.put(unitaryName, WinPQParser.parseDouble(optionalDouble, unitaryName));
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}
//		this.model.setThdData(data);
//	}
//
//	private void parseL1HarmonicsToDouble(List<String> harmonicsFileDataList, UnitaryNames unitaryName, Integer columnHarmonicsID) {
//		Map<UnitaryNames, Double> data;
//		data = this.model.getHarmonicsL1Data();
//		String optionalDouble = harmonicsFileDataList.get(columnHarmonicsID + COLUMN_OFFSET);
//		try {
//			data.put(unitaryName, WinPQParser.parseDouble(optionalDouble, unitaryName));
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}
//		this.model.setHarmonicsL1Data(data);
//	}
//
//	private void parseL2HarmonicsToDouble(List<String> harmonicsFileDataList, UnitaryNames unitaryName, Integer columnHarmonicsID) {
//		Map<UnitaryNames, Double> data;
//		data = this.model.getHarmonicsL2Data();
//		String optionalDouble = harmonicsFileDataList.get(columnHarmonicsID + COLUMN_OFFSET);
//		try {
//			data.put(unitaryName, WinPQParser.parseDouble(optionalDouble, unitaryName));
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}
//		this.model.setHarmonicsL2Data(data);
//	}
//
//	private void parseL3HarmonicsToDouble(List<String> harmonicsFileDataList, UnitaryNames unitaryName, Integer columnHarmonicsID) {
//		Map<UnitaryNames, Double> data;
//		data = this.model.getHarmonicsL3Data();
//		String optionalDouble = harmonicsFileDataList.get(columnHarmonicsID + COLUMN_OFFSET);
//		try {
//			data.put(unitaryName, WinPQParser.parseDouble(optionalDouble, unitaryName));
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}
//		this.model.setHarmonicsL3Data(data);
//	}
//}
