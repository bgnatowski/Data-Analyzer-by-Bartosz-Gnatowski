package agh.inzapp.inzynierka.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SavingUtils {
	public static void saveLineChart(LineChart<Number, Number> lineChart) throws IOException {
		Scene scene = new Scene(FxmlUtils.fxmlLoad("/fxml/ChartAnchorPane.fxml"), 1200, 800);
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
	public static void fastSaveChart(AnchorPane chart, String name) throws IOException{
		Scene scene = new Scene(chart, 800,400);
		scene.setFill(Color.WHITE);

		WritableImage image = new WritableImage(800,400);
		scene.snapshot(image);

		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		File newBarChartFile = new File(tempDirectory.getAbsolutePath() + File.separator + name +".png");
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", newBarChartFile);
	}

	public static void fastSaveChart(AnchorPane chart, String name, int h, int w) throws IOException{
		Scene scene = new Scene(chart, h,w);
		scene.setFill(Color.WHITE);

		WritableImage image = new WritableImage(h,w);
		scene.snapshot(image);

		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		File newBarChartFile = new File(tempDirectory.getAbsolutePath() + File.separator + name +".png");
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", newBarChartFile);
	}
}
