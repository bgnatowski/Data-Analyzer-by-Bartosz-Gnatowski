package agh.inzapp.inzynierka.utils;

import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
}
