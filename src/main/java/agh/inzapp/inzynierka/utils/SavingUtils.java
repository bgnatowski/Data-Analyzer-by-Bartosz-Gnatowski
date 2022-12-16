package agh.inzapp.inzynierka.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SavingUtils {
	public static void saveLineChart(LineChart<String, Number> lineChart) throws IOException {
		Scene scene = new Scene(new AnchorPane(), 1200, 800);
		((AnchorPane) scene.getRoot()).getChildren().add(lineChart);
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

	public static void fastSaveBarChart(BarChart<String, Number> chart, String name) throws IOException{
		Scene scene = new Scene(new AnchorPane(), 1200, 800);
		((AnchorPane) scene.getRoot()).getChildren().add(chart);
		WritableImage image = new WritableImage(1200, 800);
		scene.snapshot(image);
		File file = new File("output/"+name+".png");
		if (file != null) {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
		}
	}
}
