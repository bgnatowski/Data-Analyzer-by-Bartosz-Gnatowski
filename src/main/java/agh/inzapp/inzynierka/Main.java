package agh.inzapp.inzynierka;

import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.models.enums.FXMLNames;
import com.deepoove.poi.XWPFTemplate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

@SpringBootApplication
public class Main extends Application {
	private ConfigurableApplicationContext applicationContext;
	private Parent root;
	private String applicationTitle;

	public static void main(String[] args) {
		Application.launch(args);

		File templateFile = new File("src/main/resources/data/template.docx");
		XWPFTemplate template = XWPFTemplate.compile(templateFile.getAbsolutePath()).render(
				new HashMap<String, Object>(){{
					put("title", "Hi, poi-tl");
				}});
		try {
			template.writeAndClose(new FileOutputStream("src/main/resources/data/output.docx"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
			DialogUtils.errorDialog(e.getMessage(), e.getClass(), "error.init");
		}
	}
	@Override
	public void start(Stage stage) {
		final Scene scene = new Scene(root, 1280, 720);
		stage.setScene(scene);
		stage.setTitle(applicationTitle);
//		stage.setMaximized(true);
		stage.show();
	}
	@Override
	public void stop() {
		applicationContext.close();
		Platform.exit();
	}

}
