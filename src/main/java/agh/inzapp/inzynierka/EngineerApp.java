package agh.inzapp.inzynierka;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EngineerApp {

	public static void main(String[] args) {
//		Locale.setDefault(new Locale("eng"));
//		SpringApplication.run(EngineerApp.class, args);
		Application.launch(DataAnalysisApp.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(PQRepository pqRepository) {
//		return args -> {
//			CSVFromPQ csvStrategy = new CSVFromPQ();
//			List<BaseDataObj> modelList = new ArrayList<>();
//			String path = "src/main/resources/data/OchotnicaTrafo.csv";
//			try {
//				modelList.addAll(csvStrategy.importCSVFile(path));
//				PQDataObj modelObj = (PQDataObj) modelList.get(0);
//				PQDataDb dbModel = new PQDataDb();
//				dbModel.setDate(modelObj.getLocalDateTime().toLocalDate());
//				dbModel.setTime(modelObj.getLocalDateTime().toLocalTime());
//				dbModel.setFlag(modelObj.getFlag());
//				dbModel.setRecords(PQConverter.convertRecordsMapToDb(modelObj.getRecords()));
//				pqRepository.save(dbModel);
//
//
//			} catch (ApplicationException e) {
//				throw new RuntimeException(e);
//			}
//		};
//	}
}
