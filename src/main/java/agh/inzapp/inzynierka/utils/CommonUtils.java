package agh.inzapp.inzynierka.utils;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommonUtils {
	public static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
		LocalDate localDate1 = date1.toLocalDate();
		LocalDate localDate2 = date2.toLocalDate();
		return localDate1.isEqual(localDate2);
	}

	public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) throws ApplicationException {
		Iterator<K> keyIter = keys.iterator();
		Iterator<V> valIter = values.iterator();
		Map<K, V> map = new LinkedHashMap<>();
		while (keyIter.hasNext() && valIter.hasNext()) {
			map.put(keyIter.next(), valIter.next());
		}
		return map;
	}

	public static List<UniNames> deleteNonRecordsFromUniNamesList(List<UniNames> uniNamesList) {
		List<UniNames> collect = uniNamesList.stream().distinct().collect(Collectors.toList());
		collect.remove(UniNames.Date);
		collect.remove(UniNames.Time);
		collect.remove(UniNames.Flag);
		collect.remove(UniNames.Flag_A);
		collect.remove(UniNames.Flag_E);
		collect.remove(UniNames.Flag_G);
		collect.remove(UniNames.Flag_P);
		collect.remove(UniNames.Flag_T);
		return collect;
	}

	public static TextFormatter<Double> getDoubleTextFormatter() {
		Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

		UnaryOperator<TextFormatter.Change> filter = c -> {
			String text = c.getControlNewText();
			if (validEditingState.matcher(text).matches()) {
				return c ;
			} else {
				return null ;
			}
		};

		StringConverter<Double> converter = new StringConverter<Double>() {
			@Override
			public Double fromString(String s) {
				if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
					return 0.0 ;
				} else {
					return Double.valueOf(s);
				}
			}
			@Override
			public String toString(Double d) {
				return d.toString();
			}
		};
		TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);
		return textFormatter;
	}

	public static String convertToWebString(Color color) {
		String rgb = String.format("#%02x%02x%02x",
				(int) (color.getRed() * 255),
				(int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
		return rgb;
	}

	public static List<? extends CommonModelFx> mergeFxModelLists(List<DataFx> normalList, List<HarmoFx> harmoList) throws ApplicationException {
		if(normalList.size() != harmoList.size()) throw new ApplicationException(FxmlUtils.getInternalizedPropertyByKey("error.merge.list"));

		List<? extends CommonModelFx> commonList = normalList;
		for(int i = 0; i < commonList.size(); i++){
			final CommonModelFx commonModelFx = commonList.get(i);
			final ObservableMap<UniNames, Double> records = commonModelFx.getRecords();
			final ObservableMap<UniNames, Double> recordsHarmo = harmoList.get(i).getRecords();
			records.putAll(recordsHarmo);

			final ObservableList<UniNames> columnNames = commonModelFx.getColumnNames();
			final ObservableList<UniNames> columnNamesHarmo = harmoList.get(i).getColumnNames();
			columnNames.addAll(columnNamesHarmo);
			final List<UniNames> collectColumnNames = columnNames.stream().distinct().collect(Collectors.toList()); //bez powtorzen

			commonModelFx.setColumnNames(FXCollections.observableArrayList(collectColumnNames));
			commonModelFx.setRecords(FXCollections.observableMap(records));
		}
		return commonList;
	}

	public static Double percentile(List<Double> latencies, double percentile) {
		int index = (int) Math.ceil(percentile / 100.0 * latencies.size());
		return latencies.get(index-1);
	}
}
