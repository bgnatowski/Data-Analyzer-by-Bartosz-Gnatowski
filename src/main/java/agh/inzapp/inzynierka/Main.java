package agh.inzapp.inzynierka;

import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.models.enums.FXMLNames;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication

public class Main extends Application {
	private ConfigurableApplicationContext applicationContext;
	private Parent root;
	private String applicationTitle;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() {
		applicationTitle = FxmlUtils.getInternalizedPropertyByKey("application.title");
		applicationContext = SpringApplication.run(Main.class);
		FXMLLoader loader = FxmlUtils.getLoader(FXMLNames.MAIN.getPath());
		loader.setControllerFactory(applicationContext::getBean);
		try {
			root = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void start(Stage stage) {
		stage.setScene(new Scene(root, 900, 600));
		stage.setTitle(applicationTitle);
		stage.show();
	}

	@Override
	public void stop() {
		applicationContext.close();
		Platform.exit();
	}

}
