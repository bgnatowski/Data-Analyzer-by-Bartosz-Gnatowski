package agh.inzapp.inzynierka;

import agh.inzapp.inzynierka.DataAnalysisApp.StageReadyEvent;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
	@Value("classpath:/fxml/MainAppPane.fxml")
	private Resource mainResource;
	private String applicationTitle;

	public StageInitializer() {
		this.applicationTitle = FxmlUtils.getInternalizedPropertyByKey("application.title");
	}

	@Override
	public void onApplicationEvent(StageReadyEvent event) {
		try {
			Parent parent = FxmlUtils.fxmlLoad(mainResource.getURL());
			Stage stage = event.getStage();
			stage.setScene(new Scene(parent, 900, 600));
			stage.setTitle(applicationTitle);
			stage.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
//
	}
}
