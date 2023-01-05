package agh.inzapp.inzynierka;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Main {
	public static void main(String[] args) {
		Application.launch(DAApplication.class, args);
	}
}
