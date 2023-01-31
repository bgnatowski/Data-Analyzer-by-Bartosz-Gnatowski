package agh.inzapp.inzynierka.utils;

import agh.inzapp.inzynierka.models.enums.FXMLNames;
import com.deepoove.poi.XWPFTemplate;
import com.opencsv.CSVWriter;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SavingUtils {
	public static void saveLineChart(LineChart<Number, Number> lineChart) throws IOException {
		Scene scene = new Scene(FxmlUtils.fxmlLoad(FXMLNames.STANDALONE_CHART_PANE), 1200, 800);
		((AnchorPane) scene.getRoot()).getChildren().add(lineChart);
		scene.setFill(Color.WHITE);
		WritableImage image = new WritableImage(1200, 800);
		scene.snapshot(image);

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("PNG", "*.png")
		);

		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			final String extension = fileChooser.getSelectedExtensionFilter().getDescription();
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), extension, file);
		}
	}

	public static void fastSaveChart(AnchorPane chart, String name) {
		Platform.runLater(()->{
			Scene scene = new Scene(chart, 823, 336);
			scene.setFill(Color.WHITE);

			WritableImage image = new WritableImage(823, 336);
			scene.snapshot(image);

			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			File newBarChartFile = new File(tempDirectory.getAbsolutePath() + File.separator + name + ".png");
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", newBarChartFile);
			} catch (IOException e) {
				DialogUtils.errorDialog(FxmlUtils.getNameProperty("error.generating"));
			}
		});

	}

	public static void fastSaveChart(AnchorPane chart, String name, int h, int w) throws IOException {
		Scene scene = new Scene(chart, h, w);
		scene.setFill(Color.WHITE);

		WritableImage image = new WritableImage(h, w);
		scene.snapshot(image);

		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		File newBarChartFile = new File(tempDirectory.getAbsolutePath() + File.separator + name + ".png");
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", newBarChartFile);
	}

	public static void saveReport(String tmpReportPath, File outputFile) throws IOException {
		File sourceReport = new File(tmpReportPath);
		if (outputFile != null) {
			FileUtils.copyFile(sourceReport, outputFile);
		}
	}

	public static String saveTemporaryReport(Map<String, Object> reportResult) throws IOException {
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));

		final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdHHmm"));
		File tempReport = new File(tempDirectory.getAbsolutePath() + File.separator + "temp_report_" + date + ".docx");

		try (InputStream in = SavingUtils.class.getResourceAsStream("/data/szablon.docx");
			 BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)))) {
			// Use resource
			XWPFTemplate compile = XWPFTemplate.compile(in);
			compile.render(reportResult);
			compile.writeToFile(tempReport.getAbsolutePath());
		}

		return tempReport.getAbsolutePath();
	}

	public static void saveCSV(List<String[]> stringArray) throws IOException {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter(
						FxmlUtils.getResourceBundle().getString("fileChooser.extension"), "*.csv"));
		File outputFile = fileChooser.showSaveDialog(null);

		try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile), ';', '"', '"', "\n")) {
			writer.writeAll(stringArray);
		}

	}
}
