package agh.inzapp.inzynierka.utils;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommonUtils {
	public static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
		final Duration duration = Duration.between(date1, date2);
		return duration.toMinutes() < 1560;
	}

	public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values){
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

		StringConverter<Double> converter = new StringConverter<>() {
			@Override
			public Double fromString(String s) {
				if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
					return 0.0;
				} else {
					return Double.valueOf(s);
				}
			}

			@Override
			public String toString(Double d) {
				return d.toString();
			}
		};
		return new TextFormatter<>(converter, 0.0, filter);
	}

	public static String convertToWebString(Color color) {
		return String.format("#%02x%02x%02x",
				(int) (color.getRed() * 255),
				(int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
	}

	public static Double percentile(List<Double> latencies, double percentile) {
		int index = (int) Math.ceil(percentile / 100.0 * latencies.size());
		return latencies.get(index-1);
	}

	public static Double get95Percentile(List<Double> column, UniNames name) {
		final List<Double> sortedColumn = column.stream().sorted().toList();
		return percentile(sortedColumn, 95);
	}

	public static Double get5Percentile(List<Double> column, UniNames name) {
		final List<Double> sortedColumn = column.stream().sorted().toList();
		return percentile(sortedColumn, 5);
	}

	public static Double getAvg(List<Double> column, UniNames name) {
			OptionalDouble average = column
					.stream()
					.mapToDouble(a -> a)
					.average();
			if(average.isPresent())
				return average.getAsDouble();
		return 0d;
	}

	public static Double getMax(List<Double> column, UniNames name) {
		OptionalDouble max = column
				.stream()
				.mapToDouble(a -> a)
				.max();
		if(max.isPresent())
			return max.getAsDouble();
		return 0d;
	}

	public static Double getMin(List<Double> column, UniNames name) {
		OptionalDouble min = column
				.stream()
				.mapToDouble(a -> a)
				.min();
		if(min.isPresent())
			return min.getAsDouble();
		return 0d;
	}

	public static Double getTolerancePercentage(List<Double> column, UniNames name, Double tolerance) {
		final long amountInTolerance = column.stream().filter(result -> result <= tolerance).count();
		return ((double) amountInTolerance/column.size()) * 100d;
	}

	public static double calculatePlt(List<Double> pst1) {
		double sum = pst1
				.stream()
				.mapToDouble(pst -> (Math.pow(pst, 3)))
				.sum();
		sum = (1.0/12.0)*sum;
		return Math.pow(sum, 1.0/3.0);
	}
}
