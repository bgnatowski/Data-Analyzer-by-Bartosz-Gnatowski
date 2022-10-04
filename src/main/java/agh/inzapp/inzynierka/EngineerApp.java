package agh.inzapp.inzynierka;

import agh.inzapp.inzynierka.database.PQDataDb;
import agh.inzapp.inzynierka.models.modelObj.BaseDataObj;
import agh.inzapp.inzynierka.models.modelObj.PQDataObj;
import agh.inzapp.inzynierka.repository.PQRepository;
import agh.inzapp.inzynierka.strategies.CSVFromPQ;
import agh.inzapp.inzynierka.utils.converters.PQConverter;
import agh.inzapp.inzynierka.utils.enums.UnitaryNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SpringBootApplication
public class EngineerApp {

	public static void main(String[] args) {
//		Locale.setDefault(new Locale("eng"));
//		SpringApplication.run(EngineerApp.class, args);
		Application.launch(DataAnalysisApp.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PQRepository pqRepository) {
		return args -> {
			CSVFromPQ csvStrategy = new CSVFromPQ();
			List<BaseDataObj> modelList = new ArrayList<>();
			String path = "src/main/resources/data/OchotnicaTrafo.csv";
			try {
				modelList.addAll(csvStrategy.importCSVFile(path));
				PQDataObj modelObj = (PQDataObj) modelList.get(0);
				PQDataDb dbModel = new PQDataDb();
				dbModel.setDate(modelObj.getLocalDateTime().toLocalDate());
				dbModel.setTime(modelObj.getLocalDateTime().toLocalTime());
				dbModel.setFlag(modelObj.getFlag());
//				dbModel.setPqrecordsRecordMap(modelObj.getRecords());
				dbModel.setRecords(convert(modelObj.getRecords()));
//				pqRepository.save(dbModel);
//				dbModel.setRecords(PQConverter.convertRecordsMapToDb(modelObj.getRecords()));
				pqRepository.save(dbModel);
//				modelList.forEach(model -> {
//					PQDataObj modelObj = (PQDataObj) model;
//					PQDataDb dbModel = new PQDataDb();
//					dbModel.setDate(modelObj.getLocalDateTime().toLocalDate());
//					dbModel.setTime(modelObj.getLocalDateTime().toLocalTime());
//					dbModel.setFlag(modelObj.getFlag());
////					dbModel.setRecords(PQConverter.convertRecordsMapToDb(modelObj.getRecords()));
//					dbModel.setPqrecordsRecordMap(modelObj.getRecords());
////					dbModel.setPqrecordsRecordMap(convert(model.getRecords()));
//					pqRepository.save(dbModel);
//
//				});

			} catch (ApplicationException e) {
				throw new RuntimeException(e);
			}
		};
	}

	private Map<String, Double> convert(Map<UnitaryNames, Double> records) {
		Map<String, Double> map = new TreeMap<>();
		records.forEach((name, record) -> {
			map.put(name.toString(), record);
		});
		return map;
	}
}
