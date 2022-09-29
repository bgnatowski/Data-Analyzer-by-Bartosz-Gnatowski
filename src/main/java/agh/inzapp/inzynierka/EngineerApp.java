package agh.inzapp.inzynierka;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@SpringBootApplication
public class EngineerApp {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("eng"));
		Application.launch(DataAnalysisApp.class, args);
	}

}
