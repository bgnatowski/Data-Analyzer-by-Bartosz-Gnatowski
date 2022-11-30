package agh.inzapp.inzynierka.utils;

import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
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

	public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) throws ApplicationException {
		Iterator<K> keyIter = keys.iterator();
		Iterator<V> valIter = values.iterator();
		Map<K, V> map = new LinkedHashMap<>();
		while (keyIter.hasNext() && valIter.hasNext()) {
			map.put(keyIter.next(), valIter.next());
		}
		if (keyIter.hasNext() || valIter.hasNext()) throw new ApplicationException("map size are not the same");
		return map;
	}

}
