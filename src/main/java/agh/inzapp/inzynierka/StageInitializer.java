package agh.inzapp.inzynierka;

import agh.inzapp.inzynierka.models.enums.FXMLNames;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static agh.inzapp.inzynierka.DAApplication.StageReadyEvent;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
	private final String applicationTitle;
	private final ApplicationContext applicationContext;

	public StageInitializer(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.applicationTitle = FxmlUtils.getInternalizedPropertyByKey("application.title");
	}

	@Override
	public void onApplicationEvent(StageReadyEvent event) {
		try {
			FXMLLoader loader = FxmlUtils.getLoader(FXMLNames.MAIN);
			loader.setControllerFactory(applicationContext::getBean);
			Parent parent = loader.load();

			Stage stage = event.getStage();
			stage.setScene(new Scene(parent,1120, 620));
			stage.getIcons().addAll(
					new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/ikona.png"))),
					new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/ikona48.png"))),
					new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/ikona32.png"))),
					new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/ikona16.png")))
			);

			stage.setTitle(applicationTitle);
			stage.setMaximized(true);
			stage.setMinHeight(500);
			stage.setMinWidth(800);
			stage.show();
		} catch (IOException e) {
			DialogUtils.errorDialog(FxmlUtils.getInternalizedPropertyByKey("error.main"));
		}
	}
}
