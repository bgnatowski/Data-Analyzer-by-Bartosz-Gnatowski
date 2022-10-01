package agh.inzapp.inzynierka;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class EngineerApp {

	public static void main(String[] args) {
//		Locale.setDefault(new Locale("eng"));
		Application.launch(DataAnalysisApp.class, args);
	}

}
