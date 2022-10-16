package agh.inzapp.inzynierka;

import agh.inzapp.inzynierka.enums.UniNames;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestPq {
	public static void main(String[] args) {
		Map<UniNames, String> flagMap = new LinkedHashMap<>();
		flagMap.put(UniNames.Flag, null);

		final String s = convertFlagsMapToDb(flagMap);
		System.out.println(s);

	}
	private static Map<UniNames, String> convertFlagsMapToObj(String flagPattern) {
		Map<UniNames, String> map = new LinkedHashMap<>();
		map.put(UniNames.Flag, flagPattern);

		return map;
	}
	private static String convertFlagsMapToDb(Map<UniNames, String> flag) {
		return flag.get(UniNames.Flag);
	}
}
