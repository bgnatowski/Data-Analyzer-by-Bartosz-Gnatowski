package agh.inzapp.inzynierka;

import agh.inzapp.inzynierka.models.enums.FXMLNames;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static agh.inzapp.inzynierka.DAApplication.StageReadyEvent;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
	private String applicationTitle;
	private ApplicationContext applicationContext;

	public StageInitializer(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.applicationTitle = FxmlUtils.getInternalizedPropertyByKey("application.title");
	}

	@Override
	public void onApplicationEvent(StageReadyEvent event) {
		try {
			FXMLLoader loader = FxmlUtils.getLoader(FXMLNames.MAIN.getPath());
			loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
			Parent parent = loader.load();


			Stage stage = event.getStage();
			stage.setScene(new Scene(parent,1280, 720));
			stage.setTitle(applicationTitle);
			stage.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
