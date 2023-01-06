package agh.inzapp.inzynierka.utils;

import agh.inzapp.inzynierka.models.enums.FXMLNames;
import com.deepoove.poi.XWPFTemplate;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import javax.imageio.ImageIO;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	public static void fastSaveChart(AnchorPane chart, String name) throws IOException {
		Scene scene = new Scene(chart, 823, 336);
		scene.setFill(Color.WHITE);

		WritableImage image = new WritableImage(823, 336);
		scene.snapshot(image);

		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		File newBarChartFile = new File(tempDirectory.getAbsolutePath() + File.separator + name + ".png");
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", newBarChartFile);
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

	private static void saveReportToPDF(String tmpReportPath, File outputFile) throws IOException, Docx4JException {
		InputStream templateInputStream = new FileInputStream(tmpReportPath);
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

		FileOutputStream os = new FileOutputStream(outputFile.getAbsolutePath());
		Docx4J.toPDF(wordMLPackage, os);
		os.flush();
		os.close();
	}

	public static void saveReport(String tmpReportPath) throws IOException, Docx4JException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("MS Office Documents", "*.docx"),
				new FileChooser.ExtensionFilter("PDF Documents", "*.pdf")
		);
		File outputFile = fileChooser.showSaveDialog(null);
		File sourceReport = new File(tmpReportPath);
		if (outputFile != null) {
			final String extension = fileChooser.getSelectedExtensionFilter().getDescription();
			if (extension.equals("MS Office Documents")) {
				FileUtils.copyFile(sourceReport, outputFile);
			} else {
				saveReportToPDF(tmpReportPath, outputFile);
			}
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
}
