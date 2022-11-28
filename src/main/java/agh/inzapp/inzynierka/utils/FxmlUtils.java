package agh.inzapp.inzynierka.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FxmlUtils {
	public static Pane fxmlLoad(String fxmlPath) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
		fxmlLoader.setResources(getResourceBundle());
		return fxmlLoader.load();
	}

	public static FXMLLoader getLoader(String fxmlPath) {
		FXMLLoader fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
		fxmlLoader.setResources(getResourceBundle());
		return fxmlLoader;
	}

	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("bundles.messages");
	}

	public static String getInternalizedPropertyByKey(String key) {
		return ResourceBundle.getBundle("bundles.messages").getString(key);
	}

	public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
		Iterator<K> keyIter = keys.iterator();
		Iterator<V> valIter = values.iterator();
		return IntStream.range(0, keys.size()).boxed()
				.collect(Collectors.toMap(_i -> keyIter.next(), _i -> valIter.next()));
	}

}
